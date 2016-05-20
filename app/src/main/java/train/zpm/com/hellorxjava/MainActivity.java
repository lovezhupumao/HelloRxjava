package train.zpm.com.hellorxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

    private Observable<String> observable;
    private Subscriber<String> subscriber;
    @Bind(R.id.btn)
    Button btn;
    @OnClick(R.id.btn)void click(){
        observable=Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello");
            }
        });
        subscriber=new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                btn.setText(s);
            }
        };
        observable.subscribe(subscriber);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Observable.just("hello", "hi").subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i("onCompleted", "onCompleted");
            }

            @Override
            public void onError(Throwable throwable) {
                Log.i("onError", "onError");
            }

            @Override
            public void onNext(String s) {
                Log.i("OnNext", s);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        subscriber.unsubscribe();
        super.onDestroy();
    }
}
