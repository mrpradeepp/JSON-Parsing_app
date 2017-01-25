package com.example.lenovo.database_app;

import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XML_parser extends AppCompatActivity {
TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_parser);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv=(TextView)findViewById(R.id.textView1);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


            try {

                InputStream in = getAssets().open("file.xml");
                DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance().newInstance();
                DocumentBuilder dbuilder=dbf.newDocumentBuilder();
                Document document = dbuilder.parse(in);
                Element element=document.getDocumentElement();
                element.normalize();

                NodeList nlist=document.getElementsByTagName("employee");
                for(int i=0;i<=nlist.getLength();i++)
                {
                    Node node=nlist.item(i);
                    if(node.getNodeType()== Node.ELEMENT_NODE)
                    {
                        Element element1=(Element)node;
                        tv.setText(tv.getText()+"\n Name::"+getvalue("name",element1)+"\n");
                        tv.setText(tv.getText()+"\n Category::"+ getvalue("category",element1)+"\n");
                        tv.setText(tv.getText()+"----------");
                    }

                }

            }
            catch(Exception ex)
            {
                ex.printStackTrace();



        }


    }

    private static String getvalue(String tag,Element element)
    {
        NodeList node_list=element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node_item=node_list.item(0);
        return node_item.getNodeValue();

    }

}
