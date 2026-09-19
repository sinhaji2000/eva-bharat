# Multi-Window Media Sequencer — Backend

Spring Boot + MySQL API for a set of display windows that each loop their own media playlist inside a 5-hour cycle, plus a **sync** action that shows one media item on every window at the same time.

The React frontend lives in [`../music-player`](../music-player).

## Run locally

Requirements: Java 21, MySQL 8.

```bash
./gradlew bootRun
```

The API starts on `http://localhost:8080`. The database `ava_bharat` is created automatically, and seed data is inserted on the first start (only when the `media_items` table is empty).

### Configuration

| Env var | Default | Purpose |
| --- | --- | --- |
| `PORT` | `8080` | HTTP port |
| `DB_URL` | `jdbc:mysql://localhost:3306/ava_bharat?createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false` | JDBC URL |
| `DB_USERNAME` | `root` | DB user |
| `DB_PASSWORD` | `root` | DB password |
| `CORS_ALLOWED_ORIGINS` | `https://music-system-eva.vercel.app,http://localhost:5173,http://localhost:3000` | Comma-separated frontend origins |

## Seed data

| Code | Name | Type | Duration |
| --- | --- | --- | --- |
| M1 | Mountain Still | image | 8s |
| M2 | Forest Path | image | 8s |
| M3 | Big Buck Bunny | video | 12s |
| M4 | Coastline | image | 7s |
| M5 | Blank | blank | 4s |
| M6 | Flower | video | 10s |

| Window | Playlist |
| --- | --- |
| Window A | M1 → M2 → M3 |
| Window B | M4 → M2 → M5 → M1 |
| Window C | M3 → M6 → M2 |

## How playback works

The backend stores configuration only (windows, media, playlist items, sync events). It does **not** push "next item" events. The frontend derives what each window shows from the playlist and the server clock:

```
cycleElapsed = serverNow mod 5h          (cycles are anchored at the Unix epoch)
t            = cycleElapsed mod sum(playlist durations)
current item = the item whose [start, start + duration) window contains t
```

- The playlist repeats back to back inside the cycle, with no gaps. The list restarts from its first item at every 5-hour boundary. If the last loop of a cycle doesn't fit, it is cut short at the boundary.
- A window only goes blank when a `BLANK` item is actually in its playlist. An empty playlist shows a "No media" fallback, and a media URL that fails to load shows a fallback card.
- Every tab computes the same frame for the same instant. The client corrects for clock skew with the `serverTime` field in `/api/windows/state`.

## How sync works

1. `POST /api/sync` stores a `SyncEvent` (media, duration, `startedAt = now`) and deactivates any earlier active sync. Only one sync is live at a time, and a new sync replaces the running one.
2. `GET /api/windows/state` includes the live sync (`startedAt`, `endsAt`) while `now < startedAt + duration`.
3. While a sync is live, every window renders the sync media instead of its own frame. Videos seek to `now - startedAt`, so all windows show the same moment.
4. When `endsAt` passes, each window goes back to its own timeline. The client checks `endsAt` locally on every tick, so windows resume at the same instant even between polls. Playlists are never modified by a sync.

Because the timeline is time-based, a window resumes where its sequence would be "now", as if it had kept playing behind the sync. It does not resume from the item it was showing when the sync started.

## API

All endpoints are under `/api`. Errors return `{"message": "..."}` with a 4xx status.

| Method | Path | Body | Description |
| --- | --- | --- | --- |
| GET | `/windows/state` | — | Everything the player needs: `serverTime`, `sync`, `windows` (with playlists), `mediaLibrary` |
| GET | `/windows` | — | Windows with their playlists |
| POST | `/windows` | `{"name": "Window D"}` | Create a window (5h cycle) |
| POST | `/windows/{windowId}/playlist` | `{"mediaId": 2, "durationSeconds": 8}` | Append media to a window's playlist. `durationSeconds` is optional and defaults to the media's duration |
| DELETE | `/windows/{windowId}/playlist/{itemId}` | — | Remove a playlist item |
| GET | `/media` | — | Media library |
| POST | `/media` | `{"code": "M7", "name": "...", "mediaType": "IMAGE\|VIDEO\|BLANK", "mediaUrl": "https://...", "durationSeconds": 8}` | Create media (`mediaUrl` required unless `BLANK`) |
| POST | `/sync` | `{"mediaId": 2, "durationSeconds": 10}` | Show a media item on all windows. `durationSeconds` defaults to 10 |
| GET | `/sync` | — | Current sync state (`active: false` when none) |

Example:

```bash
curl -X POST localhost:8080/api/sync -H 'Content-Type: application/json' -d '{"mediaId": 2, "durationSeconds": 15}'
```

## Deployment

The backend needs a MySQL database. Any managed MySQL works (Railway, Aiven, PlanetScale-compatible, RDS).

1. Create a MySQL database and note its host, port, database name, user and password.
2. Deploy this folder with the included `Dockerfile` to any container host (Render, Railway, Fly.io):
   - `DB_URL=jdbc:mysql://<host>:<port>/<db>?useSSL=true`
   - `DB_USERNAME`, `DB_PASSWORD`
   - `CORS_ALLOWED_ORIGINS=https://<your-frontend-domain>`
   - `PORT` is usually injected by the platform.
3. Tables are created on startup (`ddl-auto: update`), and seed data is inserted into an empty database.
4. Check it with `GET https://<backend>/api/windows/state`.

Without Docker: `./gradlew bootJar` and run `java -jar build/libs/ava-bharat-0.0.1-SNAPSHOT.jar` with the same env vars.

## Assumptions and tradeoffs

- **The 5-hour cycle is anchored at the Unix epoch**, not at window creation. This keeps the math stateless and gives every client the same timeline.
- **Adding or removing an item changes the loop length**, so the current position in that window jumps to wherever the new timeline says. The alternative is storing a per-window "playlist changed at" anchor. That would be smoother, but it adds state and wasn't needed for correctness.
- **Polling (1.5s) instead of WebSockets.** Playlist changes and a new sync reach other open tabs within one poll interval. The tab that triggers the sync applies it immediately. Sync *end* is computed locally, so it's exact.
- **The duration is per playlist item**, so the same media can play for different lengths in different windows. Videos that are shorter than their slot loop, and longer ones are cut at the slot end.
- Videos autoplay muted, because browsers block autoplay with sound.
