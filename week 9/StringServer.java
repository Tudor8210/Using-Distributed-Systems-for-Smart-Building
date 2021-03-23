package grpc.examples.strings;

import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import grpc.examples.strings.StringsServiceGrpc.StringsServiceImplBase;
import io.grpc.stub.StreamObserver;


public class StringServer extends StringsServiceImplBase {


	private static final Logger logger = Logger.getLogger(StringServer.class.getName());

	
	 public static void main(String[] args) throws IOException, InterruptedException {
		    
		 	StringServer stringserver = new StringServer();
		   
		    int port = 50051;
		    Server server = ServerBuilder.forPort(port)
		        .addService(stringserver)
		        .build()
		        .start();
		    
		    logger.info("Server started, listening on " + port);
		    		    
		    server.awaitTermination();
	 }

	 @Override
	 public void split(StringRequest request, StreamObserver<StringResponse> responseObserver) {
		 System.out.println("receiving split");
		 
		 char[]characters = request.getVal().toCharArray();
		 System.out.println(characters.length);
		 
		 for(char c: characters) {			
			 responseObserver.onNext(StringResponse.newBuilder().setVal(Character.toString(c)).build());
		 }
		 
		 responseObserver.onCompleted();
	}
	
	@Override
	public void empty(Message request, StreamObserver<Empty> responseObserver) {
		System.out.println("empty message "+ request.getDetail());
			
		//will generate an error CANCELLED: HTTP/2 error code: CANCEL
		Empty reply = Empty.newBuilder().build();
		responseObserver.onNext(reply); 
		
	//	StatusRuntimeException er = new StatusRuntimeException(Status.ABORTED);
	//	responseObserver.onError(er);
	  
		responseObserver.onCompleted();
	}


	@Override
	public void reverse(StringRequest request, StreamObserver<StringResponse> responseObserver) {
		
		System.out.print("receiving reverse message ");
		
		StringBuilder stb = new StringBuilder(request.getVal());
		String output = stb.reverse().toString();
		
		StringResponse reply = StringResponse.newBuilder().setVal(output).build();
		
		responseObserver.onNext(reply);
		
		responseObserver.onCompleted();
	}

	

	@Override
	public StreamObserver<StringRequest> reverseStream(StreamObserver<StringResponse> responseObserver) {
		return new StreamObserver<StringRequest>() {

			@Override
			public void onNext(StringRequest request) {
					StringBuilder input1 = new StringBuilder(); 
				  
		            input1.append(request.getVal()); 
		            input1 = input1.reverse();
		        
		            StringResponse reply = StringResponse.newBuilder().setVal(input1.toString()).build();
		      
		            responseObserver.onNext(reply);
				
			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stubal
				
			}

			@Override
			public void onCompleted() {
				 responseObserver.onCompleted();
				
			}
			
		};
	}

	@Override
	public StreamObserver<StringRequest> length(StreamObserver<LengthResponse> responseObserver) {
		
		
		return new StreamObserver<StringRequest>() {
			
			int length = 0;
			
			@Override
			public void onNext(StringRequest value) {
				
				System.out.println("receive -> " + value.getVal());
				length += value.getVal().length();
				
			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onCompleted() {
				  LengthResponse res = LengthResponse.newBuilder().setLength(length).build();
		          responseObserver.onNext(res);
		          responseObserver.onCompleted();
			}
			
			
		};
	}

	
	
}

