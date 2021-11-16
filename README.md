# Spring Batch REST API

This library is meant to manage Spring Batch Jobs through a REST API.
It has been developed to be compatible with [JHipster](https://www.jhipster.tech/) but only really needs [Spring](https://spring.io/) and [Spring Batch](https://spring.io/projects/spring-batch) to run properly.

This lib is meant to be used with the following front-end for Angular : [angular-spring-batch](https://github.com/nicoraynaud/angular-spring-batch).

## Authors

This lib was developed by the following persons (if any co-author wants, I can add his full name and github or personal page link right here) :
- Jérome
- Alexis
- Noémie
- Kilian
- Nicolas (me)

## JHipster compatibility


| JHipster      | spring-batch-rest-api |
| ------------- | --------------------- |
|  7.x          | 0.4.x                 |
|  5,6.x        | 0.3.x                 |


## SpringBoot compatibility

| Spring Boot   | spring-batch-rest-api |
| ------------- | --------------------- |
|  2.4.x        | 0.4.x                 |
|  2.0.x        | 0.3.x                 |


## API Description

This is the swagger documentation of the exposed URLs by the lib : 

You can use [Swagger Editor](https://editor.swagger.io/) to copy paste the following yml and browse the whole doc :

```yaml
swagger: '2.0'
info:
  description: Spring Batch REST API endpoints documentation
  version: 0.1.0
  title: Spring Batch REST API
  contact: {}
  license: {}
host: localhost:8080
basePath: "/"
tags:
- name: jobs-resource
  description: Jobs Resource
paths:
  "/management/jobs":
    get:
      tags:
      - jobs-resource
      summary: getJobs
      operationId: getJobsUsingGET
      produces:
      - "*/*"
      parameters:
      - name: jobName
        in: query
        description: jobName
        required: false
        type: string
      responses:
        '200':
          description: OK
          schema:
            type: array
            items:
              "$ref": "#/definitions/JobDescriptionDTO"
        '401':
          description: Unauthorized
        '403':
          description: Forbidden
        '404':
          description: Not Found
      deprecated: false
  "/management/jobs/executions":
    get:
      tags:
      - jobs-resource
      summary: getJobExecutions
      operationId: getJobExecutionsUsingGET
      produces:
      - "*/*"
      parameters:
      - name: offset
        in: query
        required: false
        type: integer
        format: int64
      - name: page
        in: query
        description: Page number of the requested page
        required: false
        type: integer
        format: int32
      - name: pageNumber
        in: query
        required: false
        type: integer
        format: int32
      - name: pageSize
        in: query
        required: false
        type: integer
        format: int32
      - name: paged
        in: query
        required: false
        type: boolean
      - name: size
        in: query
        description: Size of a page
        required: false
        type: integer
        format: int32
      - name: sort
        in: query
        description: 'Sorting criteria in the format: property(,asc|desc). Default
          sort order is ascending. Multiple sort criteria are supported.'
        required: false
        type: array
        items:
          type: string
        collectionFormat: multi
      - name: sort.sorted
        in: query
        required: false
        type: boolean
      - name: sort.unsorted
        in: query
        required: false
        type: boolean
      - name: status
        in: query
        description: status
        required: false
        type: array
        items:
          type: string
        collectionFormat: multi
      - name: unpaged
        in: query
        required: false
        type: boolean
      responses:
        '200':
          description: OK
          schema:
            "$ref": "#/definitions/PageOfJobExecutionDTO"
        '401':
          description: Unauthorized
        '403':
          description: Forbidden
        '404':
          description: Not Found
      deprecated: false
  "/management/jobs/executions/{id}":
    get:
      tags:
      - jobs-resource
      summary: getJobExecution
      operationId: getJobExecutionUsingGET
      produces:
      - "*/*"
      parameters:
      - name: id
        in: path
        description: id
        required: true
        type: integer
        format: int64
      responses:
        '200':
          description: OK
          schema:
            "$ref": "#/definitions/JobExecutionDTO"
        '401':
          description: Unauthorized
        '403':
          description: Forbidden
        '404':
          description: Not Found
      deprecated: false
  "/management/jobs/executions/{id}/stepexecutions":
    get:
      tags:
      - jobs-resource
      summary: getJobExecutionStepExecutions
      operationId: getJobExecutionStepExecutionsUsingGET
      produces:
      - "*/*"
      parameters:
      - name: id
        in: path
        description: id
        required: true
        type: integer
        format: int64
      responses:
        '200':
          description: OK
          schema:
            type: array
            items:
              "$ref": "#/definitions/StepExecutionDTO"
        '401':
          description: Unauthorized
        '403':
          description: Forbidden
        '404':
          description: Not Found
      deprecated: false
  "/management/jobs/executions/{id}/stop":
    post:
      tags:
      - jobs-resource
      summary: stop
      operationId: stopUsingPOST
      consumes:
      - application/json
      produces:
      - "*/*"
      parameters:
      - name: id
        in: path
        description: id
        required: true
        type: integer
        format: int64
      responses:
        '200':
          description: OK
          schema:
            type: boolean
        '201':
          description: Created
        '401':
          description: Unauthorized
        '403':
          description: Forbidden
        '404':
          description: Not Found
      deprecated: false
  "/management/jobs/{jobName}/executions":
    get:
      tags:
      - jobs-resource
      summary: getJobNameExecutions
      operationId: getJobNameExecutionsUsingGET
      produces:
      - "*/*"
      parameters:
      - name: executionBeginDate
        in: query
        description: executionBeginDate
        required: false
        type: string
        format: date-time
      - name: executionEndDate
        in: query
        description: executionEndDate
        required: false
        type: string
        format: date-time
      - name: jobName
        in: path
        description: jobName
        required: true
        type: string
      - name: offset
        in: query
        required: false
        type: integer
        format: int64
      - name: page
        in: query
        description: Page number of the requested page
        required: false
        type: integer
        format: int32
      - name: pageNumber
        in: query
        required: false
        type: integer
        format: int32
      - name: pageSize
        in: query
        required: false
        type: integer
        format: int32
      - name: paged
        in: query
        required: false
        type: boolean
      - name: size
        in: query
        description: Size of a page
        required: false
        type: integer
        format: int32
      - name: sort
        in: query
        description: 'Sorting criteria in the format: property(,asc|desc). Default
          sort order is ascending. Multiple sort criteria are supported.'
        required: false
        type: array
        items:
          type: string
        collectionFormat: multi
      - name: sort.sorted
        in: query
        required: false
        type: boolean
      - name: sort.unsorted
        in: query
        required: false
        type: boolean
      - name: status
        in: query
        description: status
        required: false
        type: string
      - name: unpaged
        in: query
        required: false
        type: boolean
      responses:
        '200':
          description: OK
          schema:
            "$ref": "#/definitions/PageOfJobExecutionDTO"
        '401':
          description: Unauthorized
        '403':
          description: Forbidden
        '404':
          description: Not Found
      deprecated: false
  "/management/jobs/{jobName}/start":
    post:
      tags:
      - jobs-resource
      summary: start
      operationId: startUsingPOST
      consumes:
      - application/json
      produces:
      - "*/*"
      parameters:
      - name: jobName
        in: path
        description: jobName
        required: true
        type: string
      - in: body
        name: jobParameters
        description: jobParameters
        required: true
        schema:
          type: array
          items:
            "$ref": "#/definitions/JobExecutionParameterInputDTO"
      responses:
        '200':
          description: OK
          schema:
            "$ref": "#/definitions/JobExecutionDTO"
        '201':
          description: Created
        '401':
          description: Unauthorized
        '403':
          description: Forbidden
        '404':
          description: Not Found
      deprecated: false
  "/management/jobs/{name}":
    get:
      tags:
      - jobs-resource
      summary: getJob
      operationId: getJobUsingGET
      produces:
      - "*/*"
      parameters:
      - name: name
        in: path
        description: name
        required: true
        type: string
      responses:
        '200':
          description: OK
          schema:
            "$ref": "#/definitions/JobDescriptionDTO"
        '401':
          description: Unauthorized
        '403':
          description: Forbidden
        '404':
          description: Not Found
      deprecated: false
definitions:
  JobDescriptionDTO:
    type: object
    properties:
      description:
        type: string
      lastExecutionStatus:
        type: string
      name:
        type: string
      parameters:
        type: array
        items:
          "$ref": "#/definitions/JobDescriptionParameterDTO"
    title: JobDescriptionDTO
  JobDescriptionParameterDTO:
    type: object
    properties:
      defaultValue:
        type: string
      name:
        type: string
      type:
        type: string
    title: JobDescriptionParameterDTO
  JobExecutionDTO:
    type: object
    properties:
      createTime:
        type: string
        format: date-time
      endTime:
        type: string
        format: date-time
      exitCode:
        type: string
      exitMessage:
        type: string
      id:
        type: integer
        format: int64
      jobName:
        type: string
      lastUpdated:
        type: string
        format: date-time
      parameters:
        type: array
        items:
          "$ref": "#/definitions/JobExecutionParameterDTO"
      startTime:
        type: string
        format: date-time
      status:
        type: string
    title: JobExecutionDTO
  JobExecutionParameterDTO:
    type: object
    properties:
      identifyCharacter:
        type: string
      name:
        type: string
      type:
        type: string
      value:
        type: string
    title: JobExecutionParameterDTO
  JobExecutionParameterInputDTO:
    type: object
    properties:
      name:
        type: string
      value:
        type: string
    title: JobExecutionParameterInputDTO
  Link:
    type: object
    properties:
      href:
        type: string
      templated:
        type: boolean
    title: Link
  LoggerVM:
    type: object
    properties:
      level:
        type: string
      name:
        type: string
    title: LoggerVM
  MapOfstringAndLink:
    type: object
    title: MapOfstringAndLink
    additionalProperties:
      "$ref": "#/definitions/Link"
  PageOfJobExecutionDTO:
    type: object
    properties:
      content:
        type: array
        items:
          "$ref": "#/definitions/JobExecutionDTO"
      first:
        type: boolean
      last:
        type: boolean
      number:
        type: integer
        format: int32
      numberOfElements:
        type: integer
        format: int32
      pageable:
        "$ref": "#/definitions/Pageable"
      size:
        type: integer
        format: int32
      sort:
        "$ref": "#/definitions/Sort"
      totalElements:
        type: integer
        format: int64
      totalPages:
        type: integer
        format: int32
    title: PageOfJobExecutionDTO
  Pageable:
    type: object
    properties:
      offset:
        type: integer
        format: int64
      pageNumber:
        type: integer
        format: int32
      pageSize:
        type: integer
        format: int32
      paged:
        type: boolean
      sort:
        "$ref": "#/definitions/Sort"
      unpaged:
        type: boolean
    title: Pageable
  Sort:
    type: object
    properties:
      sorted:
        type: boolean
      unsorted:
        type: boolean
    title: Sort
  StepExecutionDTO:
    type: object
    properties:
      commitCount:
        type: integer
        format: int64
      endTime:
        type: string
        format: date-time
      exitCode:
        type: string
      exitMessage:
        type: string
      filterCount:
        type: integer
        format: int64
      id:
        type: integer
        format: int64
      jobExecutionId:
        type: integer
        format: int64
      lastUpdated:
        type: string
        format: date-time
      name:
        type: string
      processSkipCount:
        type: integer
        format: int64
      readCount:
        type: integer
        format: int64
      readSkipCount:
        type: integer
        format: int64
      rollbackCount:
        type: integer
        format: int64
      startTime:
        type: string
        format: date-time
      status:
        type: string
      writeCount:
        type: integer
        format: int64
      writeSkipCount:
        type: integer
        format: int64
    title: StepExecutionDTO

```

## How to use

### Dependency

This lib isn't yet publish on Maven Central (coming soon). In the meantime, it is possible to use it through [Jitpack](https://jitpack.io).

With Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>


<dependencies>
        <dependency>
            <groupId>com.github.nicoraynaud</groupId>
            <artifactId>spring-batch-rest-api</artifactId>
            <version>v0.1.0</version>
        </dependency>
</dependencies>

```

With Gradle 

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
	
dependencies {
    implementation 'com.github.nicoraynaud:spring-batch-rest-api:v0.1.0'
}
```

### Setup

By default, once the lib is added, it is configuring itself automatically on startup.
The lib will fail to start if spring boot is not already configured or if spring batch is not configured eather and the database tables don't exist.

#### @EntityScan

Since this lib contains ``entities``, it uses ``@EntityScan`` inside to locate the spring batch entities location. Because of this, if you haven't set you ``@EntityScan`` annotation on your own, your entities might not get discovered after you add the lib. To resolve this, add a ``
@EntityScan("my.package.name.domain")`` to your Spring configuration to make sure your entities are configured.

#### Security Configuration

By default, the lib will expose its API under :

```
/management/jobs
```

So you need to make sure that this path is properly secured, otherwise, you are exposing your batches to the whole world ! By chance, URLs like ``/management/*`` are secured with ADMIN role by default. If this is not the case, you can do something like the following :

```java
@Override
public void configure(HttpSecurity http) throws Exception {
    http
        .csrf()
        .disable()
        .authorizeRequests()
        ...
        .antMatchers("/api/**").authenticated()
        .antMatchers("/management/health").permitAll()
        .antMatchers("/management/info").permitAll()
        .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
}
```

#### Configuration

Once everything is setup you can add extra configuration to your application.yml file :

```yaml
spring:
  batch:
    job:
      enabled: false # This is to ensure that batches won't start with your APP but only when you run them

batch:
  enable: true # True by default, this configuration option lets you disable the REST API for Spring Batch
  jobs:        # This is an optional list of jobs descriptions. Such descriptions will be returned by the API in order for a user interface to create components for them
    - name: job # This is the real name of the job (the one qualifying the Job Bean)
      description:  My Job description
      parameters:
        - name: StringParameter
          type: STRING
          default-value: my big string
        - name: IntegerParameter
          type: INTEGER
          default-value: 1
        - name: BooleanParameter
          type: BOOLEAN
          default-value: false
    - name: job2
      description:  My description2
  enable-step-listener: true
  enable-step-multi-thread: false
```
