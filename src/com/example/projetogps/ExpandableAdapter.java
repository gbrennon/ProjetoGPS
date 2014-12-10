package com.example.projetogps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Fabio on 08/12/2014.
 */

public class ExpandableAdapter extends BaseExpandableListAdapter{

    private List<String> listGroup;
    private HashMap<String, List<String>> listData;
    private LayoutInflater inflater;

    public ExpandableAdapter(Context context, List<String> listGroup, HashMap<String, List<String>> listData) {
        this.listGroup=listGroup;
        this.listData=listData;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getGroupCount() {
        return listGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listData.get(listGroup.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listData.get(listGroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderGroup holder;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.group_item, null);
            holder = new ViewHolderGroup();
            convertView.setTag(holder);

            holder.prnCode = (TextView) convertView.findViewById(R.id.prnCode);
        }

        else {
            holder = (ViewHolderGroup) convertView.getTag();
        }

        holder.prnCode.setText(listGroup.get(groupPosition));

        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderItems holder;
        String val = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item, null);
            holder = new ViewHolderItems();
            convertView.setTag(holder);
            holder.childItem = (TextView) convertView.findViewById(R.id.childItem);
        }
        else {
            holder = (ViewHolderItems) convertView.getTag();
        }

        holder.childItem.setText(val);
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class ViewHolderGroup {
        TextView prnCode;
    }

    class ViewHolderItems {
        TextView childItem;
    }
}
