package com.zhl.androiddemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhl.androiddemo.bean.Node;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 描述：
 * Created by zhaohl on 2020-5-20.
 */
public class LinkListActivity extends AppCompatActivity {
    private TextView result;
    private Button btnReverse;
    private Node node;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linklist);
        result = findViewById(R.id.result);
        btnReverse = findViewById(R.id.btn_reverse_linklist);
        btnReverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Node node = revserseNode(LinkListActivity.this.node);
                showNode(node);
            }
        });
        node = generateLink();
        showNode(node);
        ProgressLoadingView loadingView = findViewById(R.id.loadingview);
        loadingView.startAnim();
    }

    private void showNode(Node node) {
        if(node==null){
            return;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(node.data);
        builder.append("-> ");
        Node temp = node;
        while (temp.next!=null){
            builder.append(temp.next.data);
            builder.append("-> ");
            temp = temp.next;
        }
        result.setText(builder.toString());
    }

    private Node generateLink() {
        Node head = new Node(1);
        Node temp = head;
        for(int i=2;i<=10;i++){
            Node node = new Node(i);
            temp.next = node;
            temp = node;
            if(1==10){
                node.next = null;
            }
        }
        return head;
    }

    private Node revserseNode(Node node){
        if(node==null){
            return null;
        }
        return reverseList(node);
    }

    /**
     * 递归方法
     * @param head
     * @return
     */
    public Node reverse1(Node head) {
        if (head == null || head.next == null)
            return head;
        Node temp = head.next;
        Node newHead = reverse1(head.next);
        temp.next = head;
        head.next = null;
        return newHead;
    }

    /**
     * 循环法
     * @param node
     * @return
     */
    public static Node reverseList(Node node) {
        Node pre = null;
        Node next = null;
        while (node != null) {
            next = node.next;
            node.next = pre;
            pre = node;
            node = next;
        }
        return pre;
    }
}
