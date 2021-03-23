package grpc.examples.strings;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import grpc.examples.strings.Message.Enum;
import io.grpc.stub.StreamObserver;


public class StringClient {
	private static  Logger logger = Logger.getLogger(StringClient.class.getName());

	private static StringsServiceGrpc.StringsServiceBlockingStub blockingStub;
	private static StringsServiceGrpc.StringsServiceStub asyncStub;
	
	public static void main(String[] args) throws Exception {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();

		//stubs -- generate from proto
		blockingStub = StringsServiceGrpc.newBlockingStub(channel);
		asyncStub = StringsServiceGrpc.newStub(channel);

		reverse();

		empty(); 	//passing an empty message - no server reply, error message

		reverseStream();

		//length();
		
	//	splitAsync();
		
	//	splitBlocking();


	}


	//unary rpc
	public static void reverse() {
		
		StringRequest req = StringRequest.newBuilder().setVal("Hello seD").build();

		StringResponse response = blockingStub.reverse(req);

		System.out.println(response.getVal());
	}


	public static void splitAsync() {
		
		StringRequest request = StringRequest.newBuilder().setVal("message 1 - introduction").build();

		StreamObserver<StringResponse> responseObserver = new StreamObserver<StringResponse>() {

			int count =0 ;

			@Override
			public void onNext(StringResponse value) {
				System.out.println("receiving messages " + value);
				count += 1;
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();

			}

			@Override
			public void onCompleted() {
				System.out.println("stream is completed ... received "+ count+ " messages");
			}

		};

		asyncStub.split(request, responseObserver);


		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	//blocking server-streaming
	public static void splitBlocking() {
		StringRequest request = StringRequest.newBuilder().setVal("message 1 - introduction").build();

		try {
			Iterator<StringResponse> responces = blockingStub.split(request);
			
			while(responces.hasNext()) {
				StringResponse temp = responces.next();
				System.out.println(temp.getVal());				
			}

		} catch (StatusRuntimeException e) {
			e.printStackTrace();
		}
		
	}



	public static void empty() {

		Message req = Message.newBuilder().setDetail(Enum.UNKNOWN).build();
		try {

			Empty response = blockingStub.empty(req);

			System.out.println("one response " + response.toString());

		}catch(StatusRuntimeException ex) {
			System.out.println( ex.getMessage());
			//ex.printStackTrace();
		}


	}

	public static void reverseStream() {


		StreamObserver<StringResponse> responseObserver = new StreamObserver<StringResponse>() {

			@Override
			public void onNext(StringResponse value) {

				System.out.println("revered received is " + value.getVal());

			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onCompleted() {
				// TODO Auto-generated method stub
				System.out.println("server completed");
			}



		};

		//
		StreamObserver<StringRequest> requestObserver = asyncStub.reverseStream(responseObserver);

		try {

			requestObserver.onNext(StringRequest.newBuilder().setVal("This").build());
			requestObserver.onNext(StringRequest.newBuilder().setVal("is").build());
			requestObserver.onNext(StringRequest.newBuilder().setVal("my text").build());
			requestObserver.onNext(StringRequest.newBuilder().setVal("document").build());

			System.out.println("SENDING MESSAGES");

			// Mark the end of requests
			requestObserver.onCompleted();


			// Sleep for a bit before sending the next one.
			Thread.sleep(new Random().nextInt(1000) + 500);


		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}


	}

	/**
	 * Async client-streaming example.
	 * */
	public static void length() {


		StreamObserver<LengthResponse> responseObserver = new StreamObserver<LengthResponse>() {

			@Override
			public void onNext(LengthResponse value) {
				System.out.println("receiving length: " + value.getLength());
			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onCompleted() {
				System.out.println("completed ");

			}

		};

		//
		StreamObserver<StringRequest> requestObserver = asyncStub.length(responseObserver);

		try {

			requestObserver.onNext(StringRequest.newBuilder().setVal("Ann").build());
			requestObserver.onNext(StringRequest.newBuilder().setVal("Paul").build());
			requestObserver.onNext(StringRequest.newBuilder().setVal("Maria").build());
			requestObserver.onNext(StringRequest.newBuilder().setVal("Nick").build());

			System.out.println("SENDING MESSAGES");

			// Mark the end of requests
			requestObserver.onCompleted();


			// Sleep for a bit before sending the next one.
			Thread.sleep(new Random().nextInt(1000) + 500);


		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}


	}

}

