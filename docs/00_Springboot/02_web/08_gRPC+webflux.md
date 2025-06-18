# gRPC (Google `Remote Procedure Call`) 
- open-source
- language-agnostic
- highly efficient for `microservices` comm.
- `binary` serialized **Protobuf** payloads  (Harder to debug)

## gRPC over HTTP/2 (default)
- gRPC with HTTP/1.1 (Less Common but Possible)
- reduced latency :point_left:
- **bi-directional streaming** - Server streaming, client streaming, and bi-directional streaming.
- **multiplexing** -  multiple calls on a single connection
- **load balancing**
- built in - Authentication, compression, SSL, and retries

## gRPC-Web((For Browser Clients)
- ng/react uses 
- gRPC-Web, which uses HTTP/1.1 or HTTP/2 in the browser to communicate with the backend gRPC services

## Steps
- proto file : [user.proto.bkp](..%2F..%2Fsrc%2Fmain%2Fproto%2Fuser.proto.bkp)
- pom.xml
- mvn compile.
- check target folder for stub.
```
<plugin>
				<groupId>org.xolstice.maven.plugins</groupId>
				<artifactId>protobuf-maven-plugin</artifactId>
				<version>0.6.1</version>
				<executions>
					<execution>
						<goals>
							<goal>compile</goal>
							<goal>compile-custom</goal>
						</goals>
					</execution>
				</executions>
				<configuration>
					<protoSourceRoot>src/main/proto</protoSourceRoot>
					<pluginId>grpc-java</pluginId>
				</configuration>
</plugin>
```
```
		<!--g-rpc-->
		<dependency>
			<groupId>io.grpc</groupId>
			<artifactId>grpc-netty</artifactId>
			<version>1.57.0</version>
		</dependency>
		<dependency>
			<groupId>io.grpc</groupId>
			<artifactId>grpc-protobuf</artifactId>
			<version>1.57.0</version>
		</dependency>
		<dependency>
			<groupId>io.grpc</groupId>
			<artifactId>grpc-stub</artifactId>
			<version>1.57.0</version>
		</dependency>
```
- client code:
```
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import com.example.grpc.UserProto;
import com.example.grpc.UserServiceGrpc;

public class UserGrpcClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
            .usePlaintext()
            .build();

        UserServiceGrpc.UserServiceBlockingStub blockingStub = UserServiceGrpc.newBlockingStub(channel);

        // Create a request
        UserProto.UserRequest request = UserProto.UserRequest.newBuilder()
            .setUserId("123")
            .build();

        // Make a gRPC call
        UserProto.UserResponse response = blockingStub.getUser(request);
        System.out.println("User Details: " + response.getName() + ", " + response.getEmail());

        channel.shutdown();
    }
}

```