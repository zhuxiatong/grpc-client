package zxt.grpc.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.justtest.GreeterGrpc;
import io.grpc.justtest.TestRequest;
import io.grpc.justtest.TestResponse;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ClientApplication {

//    public static void main(String[] args) {
//        SpringApplication.run(ClientApplication.class, args);
//    }
    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;
    private static final String host="127.0.0.1";
    private static final int ip=50051;
    public  ClientApplication(String host,int port){
        //usePlaintext表示明文传输，否则需要配置ssl
        //channel  表示通信通道
        channel= ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
        //存根
        blockingStub=GreeterGrpc.newBlockingStub(channel);
    }
    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
    public void  testResult(String name){
        TestRequest request=TestRequest.newBuilder().setName(name).build();
        TestResponse response=blockingStub.testSomeThing(request);
        System.out.println(response.getMessage());
    }
    public static void main(String[] args) {
        ClientApplication client=new  ClientApplication(host,ip);
        for (int i=0;i<=100;i++){
            client.testResult("<<<<<result>>>>>:"+i);
        }
    }
}
