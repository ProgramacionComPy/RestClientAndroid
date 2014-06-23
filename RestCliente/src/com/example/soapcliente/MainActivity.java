package com.example.soapcliente;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText nro1,nro2;
	private Button sumar;
	private String resultado="";
	private TareaSumar tareaSumar = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		nro1=(EditText) findViewById(R.id.nro1);
		nro2=(EditText) findViewById(R.id.nro2);
		sumar=(Button) findViewById(R.id.suma);
		
		sumar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tareaSumar = new TareaSumar();
				tareaSumar.execute();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class TareaSumar extends AsyncTask<Void, Void, Boolean> {
	@Override
	protected Boolean doInBackground(Void... params) {
		// TODO: attempt authentication against a network service.
		HttpClient httpClient = new DefaultHttpClient();
		 
	    HttpPost post = new  HttpPost("http://192.168.0.4:8084/ServicioWebRest/rest/suma/sumar");
	 
	    post.setHeader("content-type", "application/json");
	 
	    try
	        {
	        //Construimos el objeto cliente en formato JSON
	        JSONObject dato = new JSONObject();
	 
	        dato.put("nro1", nro1.getText().toString());
	        dato.put("nro2", nro2.getText().toString());
	 
	        StringEntity entity = new StringEntity(dato.toString());
	        post.setEntity(entity);
	 
	        HttpResponse resp = httpClient.execute(post);
	        resultado = EntityUtils.toString(resp.getEntity());
	 
	        }
	        catch(Exception ex)
	        {
	            Log.e("ServicioRest","Error!", ex);
	            return false;
	        }
	 
	        return true;
	}

	@Override
	protected void onPostExecute(final Boolean success) {
		if(success==false){
			Toast.makeText(getApplicationContext(), 	"Error", Toast.LENGTH_LONG).show();	
		}
		else{
			Toast.makeText(getApplicationContext(), 	"El resultado es: "+resultado, Toast.LENGTH_LONG).show();	
		}
	}

	@Override
	protected void onCancelled() {
		Toast.makeText(getApplicationContext(), 	"Error", Toast.LENGTH_LONG).show();
	}
}

}
