package com.zhl.androiddemo.rxjava;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zhl.androiddemo.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxJavaMainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_main);
        Button btn_Create = findViewById(R.id.m_create);
        btn_Create.setOnClickListener(this);
        Button btn_just = findViewById(R.id.m_just);
        btn_just.setOnClickListener(this);
        Button btn_interval = findViewById(R.id.m_interval_range);
        btn_interval.setOnClickListener(this);
        Button btn_map = findViewById(R.id.m_map);
        btn_map.setOnClickListener(this);
        Button btn_flat_map = findViewById(R.id.m_flat_map);
        btn_flat_map.setOnClickListener(this);
        Button btn_concat_merge = findViewById(R.id.m_concat_merge);
        btn_concat_merge.setOnClickListener(this);
        Button btn_m_flowable = findViewById(R.id.m_flowable);
        btn_m_flowable.setOnClickListener(this);
    }

    // 简单的创建调用
    private void m_Create() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("create方式创建Observable");
                emitter.onNext("发送事件1");
                emitter.onNext("发送事件2");
                emitter.onNext("发送事件3");
                emitter.onNext("发送事件4:4*5=" + 4 * 5);
                emitter.onComplete();
            }
        });
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                LogDebug.d("new Observer 创建observer");
            }

            @Override
            public void onNext(@NonNull String s) {
                LogDebug.d(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogDebug.d("onError:" + e.getMessage());
            }

            @Override
            public void onComplete() {
                LogDebug.d("onComplete");
            }
        };
        observable.subscribe(observer);
    }

    private void m_Just() {
        Observable.just("事件一", "事件二").subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                LogDebug.d("just方式创建observable");
            }

            @Override
            public void onNext(@NonNull String s) {
                LogDebug.d(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LogDebug.d("onError");
            }

            @Override
            public void onComplete() {
                LogDebug.d("just onComplete");
            }
        });
        Observable.just("Consumer", "event1", "event2").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Throwable {
                LogDebug.d(s);
            }
        });
    }

    private void m_interval_range() {
        Observable.intervalRange(0, 10, 1, 2, TimeUnit.SECONDS).doOnNext(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Throwable {
                LogDebug.d("第" + aLong + "次轮询");
            }
        }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Long aLong) {
                LogDebug.d(aLong + "");
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                LogDebug.d("onComplete");
            }
        });
    }

    private void m_map() {
        Observable.just("qeag", "adfadf", "adf235y", "q35yer")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Throwable {
                        return s.toUpperCase();
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String o) throws Throwable {
                LogDebug.d("变换后：" + o);
            }
        });
    }

    /**
     * flat_map 模拟嵌套回调
     */
    private void m_FlatMap() {
        Observable<String> observable_regiester = Observable.just("发送注册请求");
        final Observable<String> observable_login = Observable.just("发送登录请求");
        observable_regiester.doOnNext(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Throwable {
                LogDebug.d("注册成功开始登录:" + o);
            }
        }).flatMap(new Function<String, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(String s) throws Throwable {
                return observable_login;
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Throwable {
                LogDebug.d("登录成功:" + o);
            }
        });
    }

    /**
     * 合并多个observable 发送 合并后 concat 串行执行 merge 并行执行
     */
    private void m_ConcatAndMerge() {
        Observable.concat(Observable.just("被观察者1-event1", "被观察者1-event2"), Observable.just("被观察者2-event1", "被观察者2-event2"))
                .startWith(Observable.just("concat 合并后串行执行")).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Throwable {
                LogDebug.d(s);
            }
        });

        Observable.merge(Observable.just("被观察者3-event1", "被观察者3-event2"), Observable.just("被观察者4-event1", "被观察者4-event2"))
                .startWith(Observable.just("merge 合并后并行执行")).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Throwable {
                LogDebug.d(s);
            }
        });

    }

    private void m_Flowable(){
        Flowable.just("sdssd").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Throwable {
                LogDebug.d(s);
            }
        });
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<String> emitter) throws Throwable {
//                for(int i =0;i<20;i++){
//                    emitter.onNext("第"+i+"次事件");
//                    Thread.sleep(50);
//                }
                int n = (int) emitter.requested();
                LogDebug.d("emitter.requested()="+n);
                for(int i=0;i<n;i++){
                    emitter.onNext("同步第"+i+"次事件");
                }
            }
        }, BackpressureStrategy.ERROR).subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                // 异步任务背压策略这里的observer必须设置reqeust 接收事件的个数
                s.request(10);
                LogDebug.d("背压连接");
            }
            @Override
            public void onNext(String s) {
                LogDebug.d(s);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                LogDebug.d("背压 onComplete");
            }
        });
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<String> emitter) throws Throwable {
//                for(int i =0;i<20;i++){
//                    emitter.onNext("第"+i+"次事件");
//                    Thread.sleep(50);
//                }
                int n = (int) emitter.requested();
                LogDebug.d("emitter.requested()="+n);
                for(int i=0;i<n;i++){
                    emitter.onNext("异步第"+i+"次事件");
                }
            }
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                // 异步任务背压策略这里的observer必须设置reqeust 接收事件的个数
                s.request(20);
                LogDebug.d("背压连接");
            }
            @Override
            public void onNext(String s) {
                LogDebug.d(s);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                LogDebug.d("背压 onComplete");
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.m_create:
                m_Create();
                break;
            case R.id.m_just:
                m_Just();
                break;
            case R.id.m_interval_range:
                m_interval_range();
                break;
            case R.id.m_map:
                m_map();
                break;
            case R.id.m_flat_map:
                m_FlatMap();
                break;
            case R.id.m_concat_merge:
                m_ConcatAndMerge();
                break;
            case R.id.m_flowable:
                m_Flowable();
                break;
        }
    }
}