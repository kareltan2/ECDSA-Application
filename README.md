# ECDSA-Application

An ECDSA application (GUI Based) which was built by Karel Tan. 
Karel Tan is an undergraduate student in BINUS University who pursuing double degree (Computer Science and Mathematics).
This application is intended to fulfill the thesis of double degree which Karel pursues.

# General Information
This application is intended to protect data (in this case hand signature) and change the way of signature documents.

# Features:
- Generate Pair Key
- Generate Signature (from pdf document only)
- Verify Document With Signature
- QnA
- About

## Getting Started

### Prerequisites

```
- java 11
- maven 3.6.x
```

### Installing

```
to install
- mvn clean install

```

## Running the tests

```
- mvn clean compile test
```

### And coding style tests

```
Google Code Style
```

## Deployment
- [semantic version](https://devhints.io/semver)
```
to update version : (semantic version format : major.minor.patch-buildNumber)
- mvn versions:set -DnewVersion=x.xx.x-x-SNAPSHOT
- mvn versions:commit

```

## Built With

* [Maven](https://maven.apache.org/)

## Versioning

We use [SemVer](http://semver.org/) for versioning.

## Index of MainPage Tab
```
- 0 -> Key generation
- 1 -> Signing
- 2 -> Verification
- 3 -> About
- 4 -> QnA
```
