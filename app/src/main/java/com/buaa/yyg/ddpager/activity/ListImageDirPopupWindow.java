package com.buaa.yyg.ddpager.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.buaa.yyg.ddpager.R;
import com.buaa.yyg.ddpager.domain.ImageFloder;
import com.buaa.yyg.ddpager.utils.BasePopupWindowForListView;
import com.buaa.yyg.ddpager.utils.CommonAdapter;
import com.buaa.yyg.ddpager.utils.ViewHolder;

import java.util.List;

/**
 * Created by yyg on 2016/5/18.
 */
public class ListImageDirPopupWindow extends BasePopupWindowForListView{

    private ListView mListDir;
    private OnImageDirSelected mImageDirSelected;

    public ListImageDirPopupWindow(int width, int height, List<ImageFloder> datas, View convertView) {
        super(convertView, width, height, true, datas);
    }

    @Override
    protected void beforeInitWeNeedSomeParams(Object... params) {
    }

    @Override
    public void initViews() {
        mListDir = (ListView) findViewById(R.id.id_list_dir);
        mListDir.setAdapter(new CommonAdapter<ImageFloder>(context, mDatas, R.layout.list_dir_item) {
            @Override
            public void convert(ViewHolder helper, ImageFloder item, int position) {
                helper.setText(R.id.id_dir_item_name, item.getName());
                helper.setImageByUrl(R.id.id_dir_item_image, item.getFirstImagePath());
                helper.setText(R.id.id_dir_item_count, item.getCount() + "张");
            }

        });
    }

    @Override
    public void initEvents() {
        mListDir.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mImageDirSelected != null) {
                    mImageDirSelected.selected((ImageFloder) mDatas.get(position));
                }
            }
        });
    }

    @Override
    public void init() {

    }

    public interface OnImageDirSelected {
        void selected(ImageFloder floder);
    }

    public void setOnImageDirSelected(OnImageDirSelected mImageDirSelected) {
        this.mImageDirSelected = mImageDirSelected;
    }
}
