package dis.ufro.cliente;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.keyes.youtube.OpenYouTubePlayerActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ClienteYoutube extends Activity {

	private Button btnBuscar;
	private EditText etBusqueda;
	private ListView lvRespuesta;

	private String URL = "https://gdata.youtube.com/feeds/api/videos?q=";
	private String filters = "&max-results=10&alt=json&v=2";
	private String result = "";
	private String deviceId = "xxxxx";
	private final String tag = "Youtube@Response: ";

	private String[] idVideo;
	private String[] titulo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btnBuscar = (Button) findViewById(R.id.btn_buscar);
		etBusqueda = (EditText) findViewById(R.id.et_busqueda);
		lvRespuesta = (ListView) findViewById(R.id.lv_main_respuesta_busqueda);

		/*
		 * Accion boton Buscar
		 */
		btnBuscar.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if(validaBusqueda()==true){
					String busqueda = etBusqueda.getText().toString();
					callWebService(busqueda);
					infoRespuesta();
					if (idVideo != null) {
						listarRespuesta();
					} else {
						Toast.makeText(getApplicationContext(), "No se han encontrado coincidencias",
								Toast.LENGTH_LONG).show();
					}
				}
			}
		});

	}

	/**
	 * LLamada al webservice de youtube
	 * 
	 * @param q
	 *            String texto de busqueda
	 */
	public void callWebService(String q) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet request = new HttpGet(URL + q + filters);
		request.addHeader("deviceId", deviceId);
		ResponseHandler<String> handler = new BasicResponseHandler();
		try {
			result = httpclient.execute(request, handler);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		httpclient.getConnectionManager().shutdown();
		Log.i(tag, result);
	}

	/**
	 * solicita la informacion de la busqueda    
	 */
	public void infoRespuesta() {

		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(result);
			JSONObject result = (JSONObject) jsonObject.get("feed");
			JSONArray arrayResult = (JSONArray) result.getJSONArray("entry");
			int largoArray = arrayResult.length();
			idVideo = new String[largoArray];
			titulo = new String[largoArray];

			for (int i = 0; i < largoArray; i++) {

				JSONObject entry = (JSONObject) arrayResult.getJSONObject(i)
						.get("media$group");
				JSONObject mediaGroup = (JSONObject) entry.get("media$title");
				JSONObject idVideo = (JSONObject) entry.get("yt$videoid");

				this.titulo[i] = mediaGroup.getString("$t");
				this.idVideo[i] = idVideo.getString("$t");
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}

	}

	/**
	 * lista las respuestas a de la busqueda
	 */
	public void listarRespuesta() {
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, titulo);
		lvRespuesta.setAdapter(adaptador);

		lvRespuesta.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v,
					int posicion, long id) {
				reproducirVideo(idVideo[posicion]);
			}
		});
	}

	/**
	 * Inicia la reproduccion del video
	 */
	public void reproducirVideo(String id) {

		String videoId = id;
		Intent videoIntent = new Intent(null, Uri.parse("ytv://" + videoId),
				this, OpenYouTubePlayerActivity.class);
		startActivity(videoIntent);
	}
	/**
	 * valida el texto en el campo de busqueda
	 * @return false si el campo esta vacio
	 */
	private boolean validaBusqueda(){
		String busqueda = etBusqueda.getText().toString();
		if(busqueda.length()<1){
			Toast.makeText(getApplicationContext(), "Ingrese un texto para buscar",
					Toast.LENGTH_LONG).show();
			return false;
		}else{
			return true;
		}
	}

}