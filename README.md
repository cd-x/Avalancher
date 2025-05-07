# Avalancher
**Distributed Unique ID Generator**

### Configurations

| Variable         | Description                            | Default Value              | Valid Range          | Required |
| ---------------- | -------------------------------------- | -------------------------- | -------------------- | -------- |
| `WORKER_ID`      | Unique ID of the worker instance       | `0`                        | `0` – `31`           | Yes      |
| `DATA_CENTER_ID` | Unique ID of the data center or region | `0`                        | `0` – `31`           | Yes      |
| `CUSTOM_EPOCH`   | Custom epoch timestamp in milliseconds | `1715000000000` (May 2024) | Any valid epoch (ms) | No       |


### Deployment

- Assuming Istio handles security aspect, Avalancher communicates directly over plain text.

- Docker-compose
```yaml
version: '3.8'

services:
  id-generator:
    image: rrishi633/avalancher:latest
    container_name: id-generator
    ports:
      - "50051:50051"
    environment:
      - WORKER_ID=2
      - DATA_CENTER_ID=10
      - CUSTOM_EPOCH=1715000000000
    restart: unless-stopped
```

### Client

Generate stub with below proto

/[v1](https://github.com/cd-x/Avalancher/blob/master/src/main/proto/avalancher.proto)

```protobuf
syntax = "proto3";

import "google/protobuf/any.proto";
import "google/protobuf/empty.proto";

option java_package = "com.avalancher.grpc";
option java_multiple_files = true;

service Avalancher{
    rpc GetWorkerId(google.protobuf.Empty) returns (WorkerIdResponse);
    rpc GetId(google.protobuf.Empty) returns (UniqueIdResponse);
    rpc GetDatacenterId(google.protobuf.Empty) returns (DataCenterIdResponse);
}

message UniqueIdResponse{
    int64 id = 1;
}
message WorkerIdResponse{
    int64 worker_id = 1;
}
message DataCenterIdResponse{
    int64 datacenter_id = 1;
}
```

command to generate client code

```shell
protoc --java_out=./gen \
       --grpc-java_out=./gen \
       --proto_path=. \
       avalancher.proto

```