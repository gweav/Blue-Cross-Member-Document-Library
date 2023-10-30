# Blue Cross Blue Shield of North Carolina - Member Document Library

## Overview

Blue Cross and Blue Shield of North Carolina (BCBSNC) is a health insurance company that operates in the state of North Carolina in the United States. The company is headquartered in Durham, North Carolina and provides a wide range of health insurance coverage options to individuals, families, and businesses throughout the state.

The current API-based implementation of the Document Library for the Member Portal utilizes multiple API layers, which have caused an increase in software complexity, and consequently, slower performance and higher cost. Our team has been tasked with building an application that reduces API complexity and utilizes ElasticSearch to provide a document search functionality. Reducing the amount of API layers will reduce complexity, cost, and overhead. Likeswise, utilizing Elasticsearch to provide a document search functionality will improve the customer experience.

## Environment Setup
### Recommendations
- If using Windows, install WSL 2 and an updated Linux distribution. The steps of this installation guide are intended for Linux users but can easily be modified for Mac OS (Docker + AWS CLI installation steps differ slightly, refer to official documentation).

### Requirements
- Java 11 is the default JDK
- Docker installation (only the Docker Engine is required, which can be installed using the apt repository on Ubuntu (recommended). Read more [here](https://docs.docker.com/engine/install/ubuntu/#install-using-the-repository))
- Node and NPM installed
- AWS CLI installed (steps below)

### Start Elasticsearch + Kibana Docker Cluster
- Ensure the .env file is located in the /BCBS/ directory
- The .env file template is below, which can also be found [here](https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html#docker-env-file)
```
# Password for the 'elastic' user (at least 6 characters)
ELASTIC_PASSWORD=

# Password for the 'kibana_system' user (at least 6 characters)
KIBANA_PASSWORD=

# Version of Elastic products
STACK_VERSION=8.6.2

# Set the cluster name
CLUSTER_NAME=docker-cluster

# Set to 'basic' or 'trial' to automatically start the 30-day trial
LICENSE=basic
#LICENSE=trial

# Port to expose Elasticsearch HTTP API to the host
ES_PORT=9200
#ES_PORT=127.0.0.1:9200

# Port to expose Kibana to the host
KIBANA_PORT=5601
#KIBANA_PORT=80

# Increase or decrease based on the available host memory (in bytes)
MEM_LIMIT=1073741824

# Project namespace (defaults to the current folder name if not set)
#COMPOSE_PROJECT_NAME=myproject
```

- Ensure the ELASTIC_PASSWORD and KIBANA_PASSWORD values are set (to whatever value you like) and the STACK_VERSION is set to "8.6.1"
- Your passwords must be alphanumeric, and cannot contain special characters such as ! or @. The bash script included in the docker-compose.yml file only operates on alphanumeric characters.
- Type ```sysctl -w vm.max_map_count=262144``` (may need sudo privilege)
- Navigate to /BCBS/
- On Linux you may need to start Docker using the command ```sudo service docker start```
- To start, run: ```docker compose up -d```
- To stop, run: ```docker compose down```

### Install AWS CLI for S3 Access
- AWS recommends creating an IAM user with the ```PowerUserAccess``` role for interacting with AWS services from the console and command line.
- For first time setup, enter the following commands to install the AWS CLI:
```
curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
unzip awscliv2.zip
sudo ./aws/install
```
- To configure the CLI, enter the commands ```aws configure sso``` and create a **default** profile. 
- If your session expires, login again using the command ```aws sso login```
- To check your connection status, use the command ```aws sts get-caller-identity```

### Start the Spring Application Server
- Ensure the ```ELASTIC_PASSWORD``` and ```ELASTIC_FINGERPRINT``` environment variables are set in the environment _where the Spring application is started_.
- The value for the ```ELASTIC_FINGERPRINT``` environment variable is unique to your Docker container's es01 node.
- The fingerprint can be found within container es01's file system (~/config/certs/es01), accessible using the command ```docker exec -it bcbs-es01-1 bash```
- Navigate to ~/config/certs/es01 and use the command ```openssl x509 -fingerprint -sha256 -noout -in es01.crt``` to obtain the value of the fingerprint.
- Exit the docker container and set ```ELASTIC_PASSWORD``` and ```ELASTIC_FINGERPRINT``` in the local environment.
- Navigate to /BCBS/
- Run the command ```./mvnw spring-boot:run```

### Start Vue
- Navigate to /BCBS/src/frontend/
- Type ```npm install```
- Type ```npm run dev```

### Ports
- Kibana / Elastic Frontend: 5601
- Elasticsearch: 9200
- Vue JS: 3000 (The application can be seen here)
- Spring-Boot: 8080

### Questions
- These steps are derived from official Elasticsearch, AWS, Docker, Vue, and Spring documentation. 
- Email gpweaver@ncsu.edu (or perhaps gpweaver@alumni.ncsu.edu).

## Authors:
    Michael Woods
    Gabe Weaver
    Jonah Gabremichael
    Jack Butler
    Godsend Cheung

## NC State Computer Science Advisors/Coordinators:
    Ms. Margaret R. Heil, Director & Advisor
    Dr. Jason King, Technical Advisor
    Dr. Aimee Allard, Communications Assistant
    Ms. Sonali Chaudhari, Teaching Assistant

## Blue Cross Sponsors:
    Matthew Layko
    Trung Nguyen
