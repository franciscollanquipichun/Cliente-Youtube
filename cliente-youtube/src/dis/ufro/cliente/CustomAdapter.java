package dis.ufro.cliente;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter{
	
	private TextView video_titulo;
	private TextView video_descripcion;
	private TextView video_autor;
	private static LayoutInflater inflater=null;

	public int getCount() {
		return 0;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;
        if(convertView==null){
        	vi = inflater.inflate(R.layout.list_row, null);
        }
        
        video_titulo = (TextView) vi.findViewById(R.id.video_title);
        video_descripcion = (TextView) vi.findViewById(R.id.video_descripcion);
        video_autor = (TextView) vi.findViewById(R.id.video_autor);
        
        return vi;            
	}

}
