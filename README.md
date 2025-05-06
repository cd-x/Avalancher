# Avalancher
**Distributed Unique ID Generator**

### Configurations

| Variable         | Description                            | Default Value              | Valid Range          | Required |
| ---------------- | -------------------------------------- | -------------------------- | -------------------- | -------- |
| `WORKER_ID`      | Unique ID of the worker instance       | `0`                        | `0` – `31`           | Yes      |
| `DATA_CENTER_ID` | Unique ID of the data center or region | `0`                        | `0` – `31`           | Yes      |
| `CUSTOM_EPOCH`   | Custom epoch timestamp in milliseconds | `1715000000000` (May 2024) | Any valid epoch (ms) | No       |


### Deployment

---
- Docker-compose
```yaml
version: '3.8'

services:
  id-generator:
    image: rrishi633/avalancher:latest
    container_name: id-generator
    ports:
      - "50051:50051"  # map gRPC port if needed
    environment:
      - WORKER_ID=2
      - DATA_CENTER_ID=10
      - CUSTOM_EPOCH=1715000000000
    restart: unless-stopped
```