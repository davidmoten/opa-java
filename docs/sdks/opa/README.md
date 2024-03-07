# Opa SDK


## Overview

Enterprise OPA documentation
<https://docs.styra.com/enterprise-opa>
### Available Operations

* [executePolicy](#executepolicy) - Execute a policy
* [executePolicyWithInput](#executepolicywithinput) - Execute a policy given an input
* [health](#health) - Verify the server is operational

## executePolicy

Execute a policy

```mermaid
classDiagram


class `result#46;5` {
  value : decimal
}

class `provenance#46;1` {
  <<Parameter>>
  value : boolean
}

class `ClientError` {
  code : string
  message : string
}

class `ClientError#46;errors#46;location` {
  file : string
  row : integer
  col : integer
}

class `executePolicy` {
  <<Path>>
  <<GET #47;v1#47;data#47;#123;path#125;>>
}

class `explain`{
  <<Parameter>>
  notes
  fails
  full
  debug
}

class `BadRequest` {
  <<Response>>
}

class `result`

class `GzipAcceptEncoding`{
  <<Parameter>>
  gzip
}

class `result#46;3` {
  value : integer
}

class `ServerError#46;1` {
  <<Response>>
}

class `ServerError`

class `result#46;1` {
  value : boolean
}

class `pretty` {
  <<Parameter>>
  value : boolean
}

class `SuccessfulPolicyEvaluation` {
  <<Response>>
  metrics : string -> string
  decision_id : string #91;O#93;
}

class `provenance` {
  version : string #91;O#93;
  build_commit : string #91;O#93;
  build_timestamp : timestamp #91;O#93;
  build_host : string #91;O#93;
  bundles : string -> string
}

class `strict-builtin-errors` {
  <<Parameter>>
  value : boolean
}

class `policyPath` {
  <<Parameter>>
  value : string
}

class `result#46;2` {
  value : string
}

class `ClientError#46;errors` {
  code : string
  message : string
}

class `metrics` {
  <<Parameter>>
  value : boolean
}

class `instrument` {
  <<Parameter>>
  value : boolean
}

class `result#46;4`

class `result#46;6` {
  value : map
}

class `result#46;4#46;1`

`result#46;4` --> "*" `result#46;4#46;1`

`result#46;1` --|> `result`

`result#46;2` --|> `result`

`result#46;3` --|> `result`

`result#46;4` --|> `result`

`result#46;5` --|> `result`

`result#46;6` --|> `result`

`ClientError#46;errors` *--> "0..1" `ClientError#46;errors#46;location`  :  location

`ClientError` --> "*" `ClientError#46;errors`  :  errors

`ServerError` --> "1" `ClientError`

`SuccessfulPolicyEvaluation` --> "0..1" `result`

`SuccessfulPolicyEvaluation` --> "0..1" `provenance`

`BadRequest` --> "1" `ClientError`

`ServerError#46;1` --> "1" `ServerError`

`executePolicy` --> "1" `policyPath`  :  path

`executePolicy` --> "1" `GzipAcceptEncoding`  :  Accept-Encoding

`executePolicy` --> "1" `pretty`  :  pretty

`executePolicy` --> "1" `provenance#46;1`  :  provenance

`executePolicy` --> "1" `explain`  :  explain

`executePolicy` --> "1" `metrics`  :  metrics

`executePolicy` --> "1" `instrument`  :  instrument

`executePolicy` --> "1" `strict-builtin-errors`  :  strict-builtin-errors

`executePolicy` ..> "1" `SuccessfulPolicyEvaluation`  :  200

`executePolicy` ..> "1" `BadRequest`  :  400

`executePolicy` ..> "1" `ServerError#46;1`  :  500
```

### Example Usage

```java
package hello.world;

import com.styra.opa.models.operations.*;
import com.styra.opa.models.operations.ExecutePolicyRequest;
import com.styra.opa.models.operations.ExecutePolicyResponse;
import com.styra.opa.models.shared.*;
import com.styra.opa.models.shared.Explain;
import com.styra.opa.models.shared.GzipAcceptEncoding;
import com.styra.opa.opa;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Optional;
import static java.util.Map.entry;

public class Application {

    public static void main(String[] args) {
        try {
            Opa sdk = Opa.builder()
                .build();

            ExecutePolicyRequest req = ExecutePolicyRequest.builder()
                .path("<value>")
                .acceptEncoding(GzipAcceptEncoding.GZIP)
                .pretty(false)
                .provenance(false)
                .explain(Explain.NOTES)
                .metrics(false)
                .instrument(false)
                .strictBuiltinErrors(false)
                .build();

            ExecutePolicyResponse res = sdk.executePolicy()
                .request(req)
                .call();

            if (res.successfulPolicyEvaluation().isPresent()) {
                // handle response
            }
        } catch (com.styra.opa.models.errors.SDKError e) {
            // handle exception
        } catch (Exception e) {
            // handle exception
        }
    }
}
```

### Parameters

| Parameter                                                                                               | Type                                                                                                    | Required                                                                                                | Description                                                                                             |
| ------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------- |
| `request`                                                                                               | [com.styra.opa.models.operations.ExecutePolicyRequest](../../models/operations/ExecutePolicyRequest.md) | :heavy_check_mark:                                                                                      | The request object to use for the request.                                                              |


### Response

**[Optional<? extends com.styra.opa.models.operations.ExecutePolicyResponse>](../../models/operations/ExecutePolicyResponse.md)**
### Errors

| Error Object          | Status Code           | Content Type          |
| --------------------- | --------------------- | --------------------- |
| models/errorsSDKError | 4xx-5xx               | */*                   |

## executePolicyWithInput

Execute a policy given an input

```mermaid
classDiagram


class `result#46;5` {
  value : decimal
}

class `provenance#46;1` {
  <<Parameter>>
  value : boolean
}

class `ClientError` {
  code : string
  message : string
}

class `ClientError#46;errors#46;location` {
  file : string
  row : integer
  col : integer
}

class `BadRequest` {
  <<Response>>
}

class `explain`{
  <<Parameter>>
  notes
  fails
  full
  debug
}

class `GzipAcceptEncoding`{
  <<Parameter>>
  gzip
}

class `result`

class `result#46;3` {
  value : integer
}

class `executePolicyWithInput` {
  <<Path>>
  <<POST #47;v1#47;data#47;#123;path#125;>>
}

class `ServerError#46;1` {
  <<Response>>
}

class `ServerError`

class `result#46;1` {
  value : boolean
}

class `pretty` {
  <<Parameter>>
  value : boolean
}

class `SuccessfulPolicyEvaluation` {
  <<Response>>
  metrics : string -> string
  decision_id : string #91;O#93;
}

class `provenance` {
  version : string #91;O#93;
  build_commit : string #91;O#93;
  build_timestamp : timestamp #91;O#93;
  build_host : string #91;O#93;
  bundles : string -> string
}

class `strict-builtin-errors` {
  <<Parameter>>
  value : boolean
}

class `result#46;2` {
  value : string
}

class `policyPath` {
  <<Parameter>>
  value : string
}

class `executePolicyWithInput Request` {
  <<RequestBody>>
  input : string -> string
}

class `ClientError#46;errors` {
  code : string
  message : string
}

class `GzipContentEncoding`{
  <<Parameter>>
  gzip
}

class `metrics` {
  <<Parameter>>
  value : boolean
}

class `instrument` {
  <<Parameter>>
  value : boolean
}

class `result#46;4`

class `result#46;6` {
  value : map
}

class `result#46;4#46;1`

`result#46;4` --> "*" `result#46;4#46;1`

`result#46;1` --|> `result`

`result#46;2` --|> `result`

`result#46;3` --|> `result`

`result#46;4` --|> `result`

`result#46;5` --|> `result`

`result#46;6` --|> `result`

`ClientError#46;errors` *--> "0..1" `ClientError#46;errors#46;location`  :  location

`ClientError` --> "*" `ClientError#46;errors`  :  errors

`ServerError` --> "1" `ClientError`

`SuccessfulPolicyEvaluation` --> "0..1" `result`

`SuccessfulPolicyEvaluation` --> "0..1" `provenance`

`BadRequest` --> "1" `ClientError`

`ServerError#46;1` --> "1" `ServerError`

`executePolicyWithInput` --> "1" `policyPath`  :  path

`executePolicyWithInput` --> "1" `GzipContentEncoding`  :  Content-Encoding

`executePolicyWithInput` --> "1" `GzipAcceptEncoding`  :  Accept-Encoding

`executePolicyWithInput` --> "1" `pretty`  :  pretty

`executePolicyWithInput` --> "1" `provenance#46;1`  :  provenance

`executePolicyWithInput` --> "1" `explain`  :  explain

`executePolicyWithInput` --> "1" `metrics`  :  metrics

`executePolicyWithInput` --> "1" `instrument`  :  instrument

`executePolicyWithInput` --> "1" `strict-builtin-errors`  :  strict-builtin-errors

`executePolicyWithInput` ..> "1" `SuccessfulPolicyEvaluation`  :  200

`executePolicyWithInput` ..> "1" `BadRequest`  :  400

`executePolicyWithInput` ..> "1" `ServerError#46;1`  :  500

`executePolicyWithInput` --> "1" `executePolicyWithInput Request`
```

### Example Usage

```java
package hello.world;

import com.styra.opa.models.operations.*;
import com.styra.opa.models.operations.ExecutePolicyWithInputRequest;
import com.styra.opa.models.operations.ExecutePolicyWithInputRequestBody;
import com.styra.opa.models.operations.ExecutePolicyWithInputResponse;
import com.styra.opa.models.shared.*;
import com.styra.opa.models.shared.Explain;
import com.styra.opa.models.shared.GzipAcceptEncoding;
import com.styra.opa.models.shared.GzipContentEncoding;
import com.styra.opa.opa;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Optional;
import static java.util.Map.entry;

public class Application {

    public static void main(String[] args) {
        try {
            Opa sdk = Opa.builder()
                .build();

            ExecutePolicyWithInputRequest req = ExecutePolicyWithInputRequest.builder()
                .path("<value>")
                .requestBody(ExecutePolicyWithInputRequestBody.builder()
                        .input(java.util.Map.ofEntries(
                                entry("key", "<value>")))
                        .build())
                .contentEncoding(GzipContentEncoding.GZIP)
                .acceptEncoding(GzipAcceptEncoding.GZIP)
                .pretty(false)
                .provenance(false)
                .explain(Explain.NOTES)
                .metrics(false)
                .instrument(false)
                .strictBuiltinErrors(false)
                .build();

            ExecutePolicyWithInputResponse res = sdk.executePolicyWithInput()
                .request(req)
                .call();

            if (res.successfulPolicyEvaluation().isPresent()) {
                // handle response
            }
        } catch (com.styra.opa.models.errors.SDKError e) {
            // handle exception
        } catch (Exception e) {
            // handle exception
        }
    }
}
```

### Parameters

| Parameter                                                                                                                 | Type                                                                                                                      | Required                                                                                                                  | Description                                                                                                               |
| ------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------- |
| `request`                                                                                                                 | [com.styra.opa.models.operations.ExecutePolicyWithInputRequest](../../models/operations/ExecutePolicyWithInputRequest.md) | :heavy_check_mark:                                                                                                        | The request object to use for the request.                                                                                |


### Response

**[Optional<? extends com.styra.opa.models.operations.ExecutePolicyWithInputResponse>](../../models/operations/ExecutePolicyWithInputResponse.md)**
### Errors

| Error Object          | Status Code           | Content Type          |
| --------------------- | --------------------- | --------------------- |
| models/errorsSDKError | 4xx-5xx               | */*                   |

## health

The health API endpoint executes a simple built-in policy query to verify that the server is operational. Optionally it can account for bundle activation as well (useful for “ready” checks at startup).

```mermaid
classDiagram


class `exclude-plugin#46;1` {
  <<Parameter>>
  value : string
}

class `UnhealthyServer` {
  code : string #91;O#93;
}

class `health` {
  <<Path>>
  <<GET #47;health>>
}

class `bundles` {
  <<Parameter>>
  value : boolean
}

class `plugins` {
  <<Parameter>>
  value : boolean
}

class `UnhealthyServer#46;1` {
  <<Response>>
}

class `exclude-plugin` {
  <<Parameter>>
}

class `HealthyServer` {
  <<Response>>
}

`exclude-plugin` --> "*" `exclude-plugin#46;1`

`UnhealthyServer#46;1` --> "1" `UnhealthyServer`

`health` --> "1" `bundles`  :  bundles

`health` --> "1" `plugins`  :  plugins

`health` --> "1" `exclude-plugin`  :  exclude-plugin

`health` ..> "1" `HealthyServer`  :  200

`health` ..> "1" `UnhealthyServer#46;1`  :  500
```

### Example Usage

```java
package hello.world;

import com.styra.opa.models.operations.*;
import com.styra.opa.models.operations.HealthRequest;
import com.styra.opa.models.operations.HealthResponse;
import com.styra.opa.models.shared.*;
import com.styra.opa.opa;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Optional;
import static java.util.Map.entry;

public class Application {

    public static void main(String[] args) {
        try {
            Opa sdk = Opa.builder()
                .build();

            HealthResponse res = sdk.health()
                .bundles(false)
                .plugins(false)
                .excludePlugin(java.util.List.of(
                    "<value>"))
                .call();

            if (res.healthyServer().isPresent()) {
                // handle response
            }
        } catch (com.styra.opa.models.errors.SDKError e) {
            // handle exception
        } catch (Exception e) {
            // handle exception
        }
    }
}
```

### Parameters

| Parameter                                                                                                                                                                                                                                                                     | Type                                                                                                                                                                                                                                                                          | Required                                                                                                                                                                                                                                                                      | Description                                                                                                                                                                                                                                                                   |
| ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `bundles`                                                                                                                                                                                                                                                                     | *Optional<? extends Boolean>*                                                                                                                                                                                                                                                 | :heavy_minus_sign:                                                                                                                                                                                                                                                            | Boolean parameter to account for bundle activation status in response. This includes any discovery bundles or bundles defined in the loaded discovery configuration.                                                                                                          |
| `plugins`                                                                                                                                                                                                                                                                     | *Optional<? extends Boolean>*                                                                                                                                                                                                                                                 | :heavy_minus_sign:                                                                                                                                                                                                                                                            | Boolean parameter to account for plugin status in response.                                                                                                                                                                                                                   |
| `excludePlugin`                                                                                                                                                                                                                                                               | List<*String*>                                                                                                                                                                                                                                                                | :heavy_minus_sign:                                                                                                                                                                                                                                                            | String parameter to exclude a plugin from status checks. Can be added multiple times. Does nothing if plugins is not true. This parameter is useful for special use cases where a plugin depends on the server being fully initialized before it can fully initialize itself. |


### Response

**[Optional<? extends com.styra.opa.models.operations.HealthResponse>](../../models/operations/HealthResponse.md)**
### Errors

| Error Object          | Status Code           | Content Type          |
| --------------------- | --------------------- | --------------------- |
| models/errorsSDKError | 4xx-5xx               | */*                   |
