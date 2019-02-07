package chirchir96.josh.memegeneretor;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import chirchir96.josh.memegeneretor.R;

public class ImageAdapter extends BaseAdapter {



    private Context mContext;

    public ImageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public Integer[] mThumbIds = {
            R.drawable.image1, R.drawable.josh,
            R.drawable.image2, R.drawable.josh1,
            R.drawable.image9, R.drawable.josh2,
            R.drawable.images, R.drawable.josh3,
            R.drawable.images5, R.drawable.josh5,
            R.drawable.images3, R.drawable.josh6,
            R.drawable.images7, R.drawable.josh7,
            R.drawable.images3
};
    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(220, 220));


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x=new Intent(mContext, MemeActivity.class);
                int item = mThumbIds[position];
                x.putExtra("clicked", item);
                mContext.startActivity(x);
            }
        });
        return imageView;
    }


}
