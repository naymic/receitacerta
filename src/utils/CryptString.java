package utils;

import java.security.MessageDigest;

public class CryptString{
	
	
	public static String crypt(Object entry) {
		try
		  {
		   MessageDigest md = MessageDigest.getInstance( "SHA" );
		   byte[] bytes = md.digest( ((String) entry).getBytes() );
		   return getString( bytes );
		  }
		  catch( Exception e )
		  {
		   e.printStackTrace();
		   return null;
		  }
	}
	
	private static String getString( byte[] bytes )
	  {
	    StringBuffer sb = new StringBuffer();
	    for( int i=0; i<bytes.length; i++ )
	    {
	      byte b = bytes[ i ];
	      sb.append( ( int )( 0x00FF & b ) );
	      if( i+1 <bytes.length )
	      {
	        sb.append( "-" );
	      }
	    }
	    return sb.toString();
	  }

}
