package com.dev.geomaster;

import android.widget.ImageView;

/**
 * Created by Eric on 12/18/2017.
 */

public interface OnclickRecyclerListener {
    void onClickListener(int position);

    void onLongClickListener(int position);

    void onClickListener(int adapterPosition, ImageView imageView);

    // void onDeleteListener(int adapterPosition);
}
