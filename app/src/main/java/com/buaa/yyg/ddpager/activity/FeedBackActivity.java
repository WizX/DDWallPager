package com.buaa.yyg.ddpager.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.buaa.yyg.ddpager.R;
import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.SyncListener;
import com.umeng.fb.model.Conversation;
import com.umeng.fb.model.Reply;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by yyg on 2016/5/15.
 * 友盟用户反馈自定义UI
 * http://bbs.umeng.com/thread-7309-1-1.html
 */
public class FeedBackActivity extends BaseActivity {

    private static final int VIEW_TYPE_COUNT = 2;
    private static final int VIEW_TYPE_DEV = 0;
    private static final int VIEW_TYPE_USER = 1;
    private ListView mListView;
    private Button sendBtn;
    private EditText inputEdit;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Conversation mConversation;
    private ReplyAdapter adapter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    public void initView() {
        setContentView(R.layout.activity_feedback);
        mListView = (ListView) findViewById(R.id.fb_reply_list);
        sendBtn = (Button) findViewById(R.id.fb_send_btn);
        inputEdit = (EditText) findViewById(R.id.fb_send_content);
        //下拉刷新组件
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.fb_reply_refresh);
    }

    @Override
    public void initData() {
        mConversation = new FeedbackAgent(this).getDefaultConversation();
        adapter = new ReplyAdapter(this);
        mListView.setAdapter(adapter);

    }

    @Override
    public void initListener() {
        sendBtn.setOnClickListener(this);
        //下拉刷新
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sync();
            }
        });
    }

    @Override
    public void progress(View v) {
        switch (v.getId()) {
            case R.id.fb_send_btn:

                String content = inputEdit.getText().toString();
                inputEdit.getEditableText().clear();
                if (!TextUtils.isEmpty(content)) {
                    //将内容添加到会话列表
                    mConversation.addUserReply(content);
                    // 刷新新ListView
                    mHandler.sendMessage(new Message());
                    //数据同步
                    sync();
                }
                break;
            default:
                break;
        }
    }

    //数据同步
    //sync方法为数据同步的方法，利用Conversation.sync方法来进行App端的数据与服务器端同步。
    //该回调接口中onReceiveDevReply方法获得的replyList为开发者在友盟用户反馈Web端的回复。
    // onSendUserReply方法获得的replyList则为本次同步数据中发送到服务器的用户反馈数据列表
    private void sync() {
        mConversation.sync(new SyncListener() {
            @Override
            public void onReceiveDevReply(List<Reply> list) {
                //SwipeRefreshLayout停止刷新
                mSwipeRefreshLayout.setRefreshing(false);
                //发送消息，刷新ListView
                mHandler.sendMessage(new Message());
                // 如果开发者没有新的回复数据，则返回
                if (list == null || list.size() < 1) {
                    return;
                }
            }

            @Override
            public void onSendUserReply(List<Reply> list) {
            }
        });
        // 更新adapter，刷新ListView
        adapter.notifyDataSetChanged();
    }

//    private void setUserInfomation() {
//        FeedbackAgent agent = new FeedbackAgent(this);
//        UserInfo info = agent.getUserInfo();
//        if (info == null)
//            info = new UserInfo();
//        Map<String, String> contact = info.getContact();
//        if (contact == null)
//            contact = new HashMap<String, String>();
//        String contact_info = contactInfoEdit.getEditableText()
//                .toString();
//        contact.put(KEY_UMENG_CONTACT_INFO_PLAIN_TEXT, contact_info);
//
//        contact.put("email", "*******");
//        //contact.put("qq", "*******");
//        //contact.put("phone", "*******");
//        //contact.put("plain", "*******");
//        info.setContact(contact);
//
//        // optional, setting user gender information.
//        info.setAgeGroup(1);
//        info.setGender("male");
//        //info.setGender("female");
//
//        agent.setUserInfo(info);
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                boolean result = fb.updateUserInfo();
//            }
//        }).start();
//    }

    class ReplyAdapter extends BaseAdapter {

        Context mContext;

        public ReplyAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mConversation.getReplyList().size();
        }

        @Override
        public Object getItem(int position) {
            return mConversation.getReplyList().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            //两种不同的item布局
            return VIEW_TYPE_COUNT;
        }

        @Override
        public int getItemViewType(int position) {
            //获取单条回复
            Reply reply = mConversation.getReplyList().get(position);
            if (Reply.TYPE_DEV_REPLY.equals(reply.type)) {
                //开发者回复item布局
                return VIEW_TYPE_DEV;
            } else {
                //用户反馈、回复item布局
                return VIEW_TYPE_USER;
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            //获取单条回复
            Reply reply = mConversation.getReplyList().get(position);
            if (convertView == null) {
                //根据type的类型来加载不同的item布局
                if (Reply.TYPE_DEV_REPLY.equals(reply.type)) {
                    //开发者的回复
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_fb_dev_reply, null);
                } else {
                    //用户的反馈、回复
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.custom_fb_user_reply, null);
                }

                //创建ViewHolder并获取各种View
                holder = new ViewHolder();
                holder.replyContent = (TextView) convertView.findViewById(R.id.fb_reply_content);
                holder.replyProgressBar = (ProgressBar) convertView.findViewById(R.id.fb_reply_progressBar);
                holder.replyStateFailed = (ImageView) convertView.findViewById(R.id.fb_reply_state_failed);
                holder.replyDate = (TextView) convertView.findViewById(R.id.fb_reply_date);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //以下是填充数据
            //设置Reply的内容
            holder.replyContent.setText(reply.content);
            //在App界面中，对于开发者的Reply来讲status没有意思
            if (!Reply.TYPE_DEV_REPLY.equals(reply.type)) {
                //根据Reply的状态来设置replyStatusFailed的状态
                if (Reply.STATUS_NOT_SENT.equals(reply.status)) {
                    holder.replyStateFailed.setVisibility(View.VISIBLE);
                } else {
                    holder.replyStateFailed.setVisibility(View.GONE);
                }

                //根据Reply的状态来设置replyProgressBar的状态
                if (Reply.STATUS_SENDING.equals(reply.status)) {
                    holder.replyProgressBar.setVisibility(View.VISIBLE);
                } else {
                    holder.replyProgressBar.setVisibility(View.GONE);
                }
            }

            //回复的时间顺序，这里仿照QQ两条Reply之间相差100000ms则展示时间
            if ((position + 1) < mConversation.getReplyList().size()) {
                Reply nextReply = mConversation.getReplyList().get(position + 1);
                if (nextReply.created_at - reply.created_at > 100000) {
                    Date replyTime = new Date(reply.created_at);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    holder.replyDate.setText(sdf.format(replyTime));
                    holder.replyDate.setVisibility(View.VISIBLE);
                }
            }
            return convertView;
        }

        class ViewHolder {
            TextView replyContent;
            ProgressBar replyProgressBar;
            ImageView replyStateFailed;
            TextView replyDate;
        }
    }
}
