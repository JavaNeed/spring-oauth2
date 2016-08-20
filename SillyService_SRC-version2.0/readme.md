SillyService_SRC
--------------

Reference URL:
-----------
https://www.youtube.com/watch?v=XnPyoxnmdX4


C:>curl -vvv -X POST "http://localhost:8080/SillyService_SRC/oauth/token?grant_type=password&client_id=my-trusted-client&username=admin&password=password"
*   Trying 127.0.0.1...
* Connected to localhost (127.0.0.1) port 8080 (#0)
> POST /SillyService_SRC/oauth/token?grant_type=password&client_id=my-trusted-client&username=admin&password=password HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.46.0
> Accept: */*
>
< HTTP/1.1 200 OK
< Server: Apache-Coyote/1.1
< Cache-Control: no-store
< Pragma: no-cache
< Content-Type: application/json;charset=UTF-8
< Transfer-Encoding: chunked
< Date: Fri, 19 Aug 2016 11:04:15 GMT
<
{"access_token":"94a18829-05d3-46db-b68f-51b9597a28a5","token_type":"bearer","refresh_token":"04808824-d4ee-450d-8a38-a0bd4266436e","expires_in":99,"scope":"read write trust"}* Connection #0 to host localhost left intact



C:\>curl -vvv "http://localhost:8080/SillyService_SRC/employee/list?access_token=8f23c2f2-53d8-4f37-9aba-cdb44b8a89b6"
*   Trying 127.0.0.1...
* Connected to localhost (127.0.0.1) port 8080 (#0)
> GET /SillyService_SRC/employee/list?access_token=94a18829-05d3-46db-b68f-51b9597a28a5 HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.46.0
> Accept: */*
>
< HTTP/1.1 200 OK
< Server: Apache-Coyote/1.1
< Content-Type: application/json;charset=UTF-8
< Transfer-Encoding: chunked
< Date: Fri, 19 Aug 2016 11:10:34 GMT
<
[{"id":4,"name":"Employee 4","salary":173000.0},{"id":7,"name":"Employee 7","salary":85500.0},{"id":5,"name":"Employee 5","salary":16000.0},{"id":1,"name":"Employee 1","salary":97500.0},{"id":6,"name":"Employee 6","salary":10500.0},{"id":9,"name":"Employee 9","salary":35000.0},{"id":3,"name":"Employee 3","salary":39500.0},{"id":0,"name":"Employee 0","salary":49500.0},{"id":2,"name":"Employee 2","salary":124500.0},{"id":8,"name":"Employee 8","salary":69500.0}]* Connection #0 to host localhost left intact

C:>curl -vvv -X POST "http://localhost:8080/SillyService_SRC/oauth/token?client_id=my-trusted-client&grant_type=refresh_token&refresh_token=04808824-d4ee-450d-8a38-a0bd4266436e"
*   Trying 127.0.0.1...
* Connected to localhost (127.0.0.1) port 8080 (#0)
> POST /SillyService_SRC/oauth/token?client_id=my-trusted-client&grant_type=refresh_token&refresh_token=b2a3b4ad-67e9-4df9-b2aa-c775cdaa4520 HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.46.0
> Accept: */*
>
< HTTP/1.1 200 OK
< Server: Apache-Coyote/1.1
< Cache-Control: no-store
< Pragma: no-cache
< Content-Type: application/json;charset=UTF-8
< Transfer-Encoding: chunked
< Date: Fri, 19 Aug 2016 11:07:37 GMT
<
{"access_token":"fd3ee6dc-32e7-4ec1-934c-5e819d13ec6f","token_type":"bearer","refresh_token":"b2a3b4ad-67e9-4df9-b2aa-c775cdaa4520","expires_in":29,"scope":"read write trust"}* Connection #0 to host localhost left intact




