package com.example.myapplicationnumba.activitys.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationnumba.R;
import com.example.myapplicationnumba.adapter.EquipmentRecycleAdapter;
import com.example.myapplicationnumba.db.GreenDaoManager;
import com.example.myapplicationnumba.entity_model.EquipmentModel;

import java.util.ArrayList;
import java.util.List;

public class ManagementFragment extends Fragment {

    private GreenDaoManager dbManager;
    private View view;//定义view用来设置fragment的layout
    public RecyclerView mCollectRecyclerView;//定义RecyclerView
    //定义以goodsentity实体类为对象的数据集合
    private ArrayList<EquipmentModel> equipmentEntities = new ArrayList<EquipmentModel>();
    //自定义recyclerveiw的适配器
    private EquipmentRecycleAdapter mCollectRecyclerAdapter;

    private TextView textView1;
    private TextView textView2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取fragment的layout
        view = inflater.inflate(R.layout.fragment_equipment, container, false);
        //对recycleview进行配置
        initRecyclerView();
        //模拟数据
        init();
        initData();
        return view;
    }
        @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView1= (TextView) getView().findViewById(R.id.add);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager.insertEquipments();
            }
        });
        textView2= (TextView) getView().findViewById(R.id.all);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<EquipmentModel> equipmentModels = dbManager.queryEquipments();
                notifyAdapter(equipmentModels);
            }
        });
    }
    private void init () {
        dbManager = new GreenDaoManager(getActivity());
        //initView();
    }

    /**
     * TODO 初始化数据
     */
    private void initData() {
        //更改为json的数据源
        for (int i=0;i<10;i++){
            EquipmentModel goodsEntity=new EquipmentModel();
            goodsEntity.setName("模拟数据"+i);
            goodsEntity.setManufacturer("100"+i);
            equipmentEntities.add(goodsEntity);
        }
    }

    /**
     * TODO 对recycleview进行配置
     */
    private void initRecyclerView() {
        //获取RecyclerView
        mCollectRecyclerView=(RecyclerView)view.findViewById(R.id.collect_recyclerView);
        //创建adapter
        mCollectRecyclerAdapter = new EquipmentRecycleAdapter(getActivity(), equipmentEntities);
        //给RecyclerView设置adapter
        mCollectRecyclerView.setAdapter(mCollectRecyclerAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mCollectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        mCollectRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        mCollectRecyclerAdapter.setOnItemClickListener(new EquipmentRecycleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, EquipmentModel data) {
                //此处进行监听事件的业务处理
                Toast.makeText(getActivity(),"我是item",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 改变展示数据
     */
    private void notifyAdapter (List<EquipmentModel> dataSource) {
        mCollectRecyclerAdapter.setDataSource(dataSource);
    }

}