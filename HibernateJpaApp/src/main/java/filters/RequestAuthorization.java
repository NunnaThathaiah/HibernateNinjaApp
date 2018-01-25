package filters;

import java.util.Base64;
import java.util.List;

import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;
import ninja.Results;

public class RequestAuthorization implements Filter {

	public final String USERNAME = "admin";
	public final String PASSWORD = "admin";

	@Override

	public Result filter(FilterChain chain, Context context) {

		String header = context.getHeader("Authorization");
		System.out.println(header);

		// seperate encriptedData
		String[] header1 = header.split(" ");
		String encHederVal = header1[1];
		System.out.println("encHederVal");
		
		// Getting decoder
		Base64.Decoder decoder = Base64.getDecoder();
		
		// Decoding string header
		String headerDecoder = new String(decoder.decode(encHederVal));
		System.out.println("Decoded string: " + headerDecoder);
		
		//separating userName and password
		String[] UserAPassword = headerDecoder.split(":");
		String userName = UserAPassword[0];
		String password = UserAPassword[1];

//		System.out.println(userName);
//		System.out.println(password);

		if (header == null || USERNAME.equals(userName) && PASSWORD.equals(password)) {
			return chain.next(context);

		} else {
			return Results.forbidden().html().template("/views/system/forbidden403.html");
		}
	}

}
