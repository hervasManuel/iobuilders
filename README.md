# Bank IOBuilders

## Table of contents

- [Quick start](#quick-start)
- [What's included](#whats-included)
- [Adminer DB Explorer](#adminer-db-explorer)
- [Swagger Open API Definition](#swagger-open-api-definition)

## Quick start
- Linux
```bash
./run.sh
```
- Mac M1
```bash
./run_macOS.sh
```
## What's included

```text
ioBuilders/
├── ioCoin/                 * Smart Contract
│   ├── build/
│   └── contracts/
│   └── migrations/
│   └── test/
│   └── run_pipeline.sh     * Compile + Deploy + Test Smart Contract
└── src/                    * API Rest Java 
│   ├── main/
│   └── test/
├── run.sh        * Start a docker-compose with project+db+dbviewer
├── run_macOS.sh  
└── mini_bank.postman_collection.json   * Postman collection
   
```
## Adminer DB Explorer
http://localhost:8080/

## Swagger Open API Definition
http://localhost:8081/swagger-ui/index.html

