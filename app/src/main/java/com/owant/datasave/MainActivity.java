package com.owant.datasave;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity{

    @BindView(R.id.main_et_title)
    EditText Username;
    @BindView(R.id.main_et_password)
    EditText mainEtPassword;
    @BindView(R.id.main_bt_login)
    Button mainBtLogin;

    private String username;
    private String password;
    private String TAG="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initListener();
        Log.d(TAG,"onCreate");
    }
    public void initListener(){
        mainBtLogin.setOnClickListener(v -> {
            username = Username.getText().toString();
            password = mainEtPassword.getText().toString();
            saveUserInfo(username,password);
            Log.d(TAG,"initListener press ok");
        });
    }
   /**
    * @Author 孙伟豪
    * @Date 2020/3/29 0029 13:38
    * 存储数据
    * 不要硬编码“ / data /”；使用Context.getFilesDir（）。getPath（）代替...（Ctrl + F1）检查信息：
    *          您的代码不应直接引用/ sdcard路径；而是使用Environment.getExternalStorageDirectory（）。getPath（）。
    *          同样，请勿直接引用/ data / data /路径；在多用户方案中，它可能有所不同。
    *          而是使用Context.getFilesDir（）。getPath（）。问题ID：SdCardPath
    */
    private void saveUserInfo(String username, String password) {
//      得到储存路径的头部
        File fileDir=this.getFilesDir();
        Log.d(TAG,"fileDir:"+fileDir);
        Log.d(TAG,fileDir+"");
        //文件头部+尾部
        File saveFile=new File(fileDir,"info.text");
        try{
            if (!saveFile.exists()) {
                saveFile.createNewFile();
                Log.d(TAG,"savaFile is null,create ok");
            }
            FileOutputStream fos=new FileOutputStream(saveFile);
            fos.write((username+"***"+password).getBytes());
            Log.d("write","OK");
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG,"ERROR");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        try {
            FileInputStream fileInputStream = this.openFileInput("info.text");
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(fileInputStream));
            String info = bufferedReader.readLine();
            String[] split = info.split("\\*\\*\\*");
            String userName = split[0];
            String password = split[1];
            Username.setText(userName);
            mainEtPassword.setText(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get file path string.
     *这里是根据用户有没有内存卡来决定存储在那个位置
     * @param context the context
     * @param dir     the dir
     * @return the string
     */
    public static String getFilePath(Context context,String dir){
        String directoryPath = "";
        //判断SD卡是否可用
        if(MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            directoryPath = context.getExternalFilesDir(dir).getAbsolutePath();
            //direcotryPath = context.getExternalFilesDir().getAbsolutePath();
        }else{
            //没内存卡就存手机机身内存中

            directoryPath = context.getFilesDir() + File.separator + dir;
            //directoryPath = context.getCacheDir() + File.separator + dir;
        }

        File file = new File(directoryPath);
        if(!file.exists()){  //判断文件目录是否已经存在
            file.mkdirs();
        }
        return directoryPath;
    }
}
