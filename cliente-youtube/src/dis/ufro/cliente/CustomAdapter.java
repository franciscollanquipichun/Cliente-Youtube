package dis.ufro.cliente;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter{
	
	private String video_titulo[];
	private String video_descripcion[];
	private String video_autor[];
	private static LayoutInflater inflater=null;
	
	public CustomAdapter(){
		
	}
	
	public CustomAdapter(Activity context, String titulo[], String descripcion[], String autor[]){
		super();
		this.video_titulo = titulo;
		this.video_descripcion = descripcion;
		this.video_autor = autor;
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return 0;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}
	
	public static class ViewHolder  
    {  
        TextView titulo; 
        TextView descripcion;
        TextView autor;
    } 

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
        if(convertView==null){
        	holder = new ViewHolder(); 
        	convertView = inflater.inflate(R.layout.list_row, null);
 
        	holder.titulo = (TextView)convertView.findViewById(R.id.video_title); 
        	holder.descripcion = (TextView)convertView.findViewById(R.id.video_descripcion);
        	holder.autor = (TextView)convertView.findViewById(R.id.video_autor);
        
        	convertView.setTag(holder);
        }
        else{
        	holder=(ViewHolder)convertView.getTag();
        }
        //set data in customListView
        holder.titulo.setText(video_titulo[position]);
        holder.descripcion.setText(video_descripcion[position]);
        holder.autor.setText(video_autor[position]);
        return convertView;
	}

}
