package com.example.lenovo.quransubjectsapp.adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.quransubjectsapp.QuranActivity;
import com.example.lenovo.quransubjectsapp.R;
import com.example.lenovo.quransubjectsapp.TafaseerActivity;
import com.example.lenovo.quransubjectsapp.WordMeaningDialog;
import com.example.lenovo.quransubjectsapp.models.Sura;
import com.example.lenovo.quransubjectsapp.models.Verses;
import com.example.lenovo.quransubjectsapp.models.VersesOfSuras;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;
import com.h6ah4i.android.widget.advrecyclerview.utils.RecyclerViewAdapterUtils;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.Context.CLIPBOARD_SERVICE;

public class ExpandableRecyclerAdapter extends AbstractExpandableItemAdapter<ExpandableRecyclerAdapter.GroupViewHolder, ExpandableRecyclerAdapter.ChildViewHolder> {
    ArrayList<VersesOfSuras> versesOfSurasList;
    int[] openArray;
    Context context;
    RecyclerViewExpandableItemManager expandableItemManager;
    private int openGroup = 0;
    private GroupViewHolder openGroupVH;
    View.OnClickListener mItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            GroupViewHolder vh = (GroupViewHolder) RecyclerViewAdapterUtils.getViewHolder(view);
            int flatposition = vh.getAdapterPosition();
            if (flatposition == RecyclerView.NO_POSITION) {
                return;
            }
            long expandablePosition = expandableItemManager.getExpandablePosition(flatposition);
            int groupPosition = RecyclerViewExpandableItemManager.getPackedPositionGroup(expandablePosition);
            int childPosition = RecyclerViewExpandableItemManager.getPackedPositionChild(expandablePosition);
            switch (view.getId()) {

                case R.id.group_text_view:
                    Intent intent = new Intent(context,QuranActivity.class);
                    intent.putExtra("currentPage", Sura.SURAPAGES[versesOfSurasList.get(groupPosition).getSuraId()]);
                    context.startActivity(intent);
//                    Toast.makeText(context, "Go to sura " + versesOfSurasList.get(groupPosition).getSuraName(), Toast.LENGTH_SHORT).show();

                    break;
                case R.id.expand_shape:
                    if (childPosition == RecyclerView.NO_POSITION) {
                        handleGroupTextClick(groupPosition, vh);
                    }
                    break;
                case R.id.group_container:
                    if (childPosition == RecyclerView.NO_POSITION) {
                        handleGroupTextClick(groupPosition, vh);
                    }

                    break;
                default:
                    break;
            }

        }
    };

    private void handleGroupTextClick(int groupPosition, GroupViewHolder vh) {
        if (isGroupExpanded(openGroup)) {
//            openGroupVH.container.setBackgroundColor(Color.parseColor("#ffddffdd"));

            openGroupVH.expandShape.setImageDrawable(context.getResources().getDrawable(R.drawable.expand_arrow1));
            openArray[openGroup] = 0;
        }
        if (isGroupExpanded(groupPosition)) {
//            vh.container.setBackgroundColor(Color.parseColor("#ffddffdd"));
            expandableItemManager.collapseGroup(groupPosition);
            vh.expandShape.setImageDrawable(context.getResources().getDrawable(R.drawable.expand_arrow1));
            openGroup = 0;
            openArray[groupPosition] = 0;
        } else {
//            vh.container.setBackgroundColor(Color.parseColor("#30c441"));
            expandableItemManager.collapseAll();
            expandableItemManager.expandGroup(groupPosition);
            vh.expandShape.setImageDrawable(context.getResources().getDrawable(R.drawable.collapse_arrow1));
            openGroup = groupPosition;
            openGroupVH = vh;
            openArray[groupPosition] = 1;
        }
    }

    private boolean isGroupExpanded(int groupPosition) {
        return expandableItemManager.isGroupExpanded(groupPosition);
    }

    private void handleChildTextClick(int groupPosition, int childPosition, GroupViewHolder vh) {
    }

    public ExpandableRecyclerAdapter(RecyclerViewExpandableItemManager expandableItemManager) {

        setHasStableIds(true);
        this.expandableItemManager = expandableItemManager;
    }

    public void setData(ArrayList<VersesOfSuras> vos) {

        openArray = new int[vos.size()];
        Arrays.fill(openArray, 0);
        versesOfSurasList = vos;
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        if (versesOfSurasList != null)
            return versesOfSurasList.size();
        return 0;
    }

    @Override
    public int getChildCount(int groupPosition) {
        if (versesOfSurasList != null)
            return versesOfSurasList.get(groupPosition).getVerses().size();
        return 0;
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
    public ExpandableRecyclerAdapter.GroupViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View v = layoutInflater.inflate(R.layout.list_group_item, parent, false);
        if (context == null) {
            context = parent.getContext();
        }
        return new GroupViewHolder(v);
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View v = layoutInflater.inflate(R.layout.verse_row, parent, false);
        return new ChildViewHolder(v);
    }

    @Override
    public void onBindGroupViewHolder(ExpandableRecyclerAdapter.GroupViewHolder holder, int groupPosition, int viewType) {
        VersesOfSuras vos = versesOfSurasList.get(groupPosition);

        holder.bind(vos, groupPosition);
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder holder, int groupPosition, int childPosition, int viewType) {
        Verses verse = versesOfSurasList.get(groupPosition).getVerses().get(childPosition);

        String fontPath = "fonts/4_A2.ttf";
        Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        holder.verseText.setText(verse.getVerse());
        holder.verseText.setTypeface(tf);
        holder.verseText.setCustomSelectionActionModeCallback((ActionMode.Callback) new ExpandableRecyclerAdapter.StyleCallback(holder.verseText, verse.getVerseID()+"",verse.getVersePage()+""));

        String pageNumText = "ص " + verse.getVersePage();
        holder.pageNum.setText(pageNumText);
        fontPath = "fonts/Kufi-LT-Regular.ttf";
        tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        holder.pageNum.setTypeface(tf);
        holder.suraName.setText("");
        int num = childPosition + 1;
        holder.verseNum.setText("" + num);
        holder.verseNumber.setText("آية " + verse.getVerseID());
        holder.partNum.setText("جزء " + Sura.getSection(verse.getVersePage()));
        holder.verseNum.setTypeface(tf);
        holder.verseNumber.setTypeface(tf);
        holder.partNum.setTypeface(tf);


        holder.bind(verse);
    }

    @Override
    public boolean onCheckCanExpandOrCollapseGroup(ExpandableRecyclerAdapter.GroupViewHolder holder, int groupPosition, int x, int y, boolean expand) {
        return false;
    }

    public class ChildViewHolder extends AbstractExpandableItemViewHolder {
        public TextView verseText, pageNum, partNum, suraName, verseNum,verseNumber;
        public ImageView copyImage;

        public ChildViewHolder(View itemView) {
            super(itemView);
            verseText = itemView.findViewById(R.id.verse_text_view);
            pageNum = itemView.findViewById(R.id.page_num);
            partNum = itemView.findViewById(R.id.part_num);
            suraName = itemView.findViewById(R.id.sura_name);
            verseNum = itemView.findViewById(R.id.verse_num_in_list);
            verseNumber = itemView.findViewById(R.id.verse_num);
            copyImage = itemView.findViewById(R.id.copy_image);

        }

        public void bind(Verses verse) {
            String fontPath = "fonts/Amiri-Regular.ttf";
            Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
            verseText.setText(verse.getVerse());
            verseText.setTypeface(tf);
            verseText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TafaseerActivity.class);
                    intent.putExtra("book_id", 0);
                    intent.putExtra("sura_id", verse.getSuraID());
                    intent.putExtra("verse_id", verse.getVerseID());
                    context.startActivity(intent);
                }
            });
            pageNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,QuranActivity.class);
                    intent.putExtra("currentPage",verse.getVersePage());
                    context.startActivity(intent);
//                    Toast.makeText(context, verse.getVersePage() + "ص", Toast.LENGTH_SHORT).show();
                }
            });
            copyImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(context, verse.getVersePage() + "ص", Toast.LENGTH_SHORT).show();
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("verse text", verseText.getText());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(context, "تم نسخ الآية", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    public class GroupViewHolder extends AbstractExpandableItemViewHolder {
        TextView groupTextView, groupCount;
        FrameLayout container;
        ImageView expandShape;

        public GroupViewHolder(View itemView) {
            super(itemView);
            groupTextView = itemView.findViewById(R.id.group_text_view);
            container = itemView.findViewById(R.id.group_container);
            container.setOnClickListener(mItemClickListener);
            groupTextView.setOnClickListener(mItemClickListener);
            expandShape = itemView.findViewById(R.id.expand_shape);
//            expandShape.setImageDrawable(context.getResources().getDrawable(R.drawable.expand_arrow1));
//            expandShape.setOnClickListener(mItemClickListener);
            groupCount = itemView.findViewById(R.id.group_count);
        }

        public void bind(VersesOfSuras vos, int groupPosition) {
            if (openArray[groupPosition] == 1) {
                expandShape.setImageDrawable(context.getResources().getDrawable(R.drawable.collapse_arrow));
            } else {
                expandShape.setImageDrawable(context.getResources().getDrawable(R.drawable.expand_arrow));
            }
            String fontPath = "fonts/Kufi-LT-Regular.ttf";
            Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
            groupTextView.setTypeface(tf);
            groupTextView.setText(vos.getSuraName());
            groupCount.setText("(" + vos.getVerses().size() + ")");
        }
    }
    private class StyleCallback implements ActionMode.Callback {
        TextView t;
        String verseId,versePage;
        StyleCallback(TextView t,String verseId,String versePage){
            this.t=t;
            this.verseId=verseId;
            this.versePage=versePage;
        }
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            menu.clear();
            mode.getMenuInflater().inflate(R.menu.stylewithnotafseeraya, menu);
            return true;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

            menu.clear();
            mode.getMenuInflater().inflate(R.menu.stylewithnotafseeraya, menu);
            return true;
        }

        @Override


        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int start = t.getSelectionStart();
            int end = t.getSelectionEnd();
            CharSequence selectedText = t.getText().subSequence(start, end);



            switch (item.getItemId()) {


                case R.id.word_tafseer:
                    CharSequence mainText = t.getText();
                    selectedText = selectedText.toString().trim();
                    if (!selectedText.toString().contains(" ")) {
                        for (int i = start; i > 0; i--) {
                            if (mainText.subSequence(i, end).toString().contains(" ") ||
                                    mainText.subSequence(i, end).toString().contains(")") ||
                                    mainText.subSequence(i, end).toString().contains("\n")) {
                                start = i + 1;
                                break;
                            }
                        }
                        for (int i = end; i < mainText.length(); i++) {
                            if (mainText.subSequence(start, i).toString().contains(" ") ||
                                    mainText.subSequence(start, i).toString().contains("(") ||
                                    mainText.subSequence(start, i).toString().contains("\n")) {
                                end = i - 1;
                                break;
                            }
                        }
                        selectedText = mainText.subSequence(start, end);
                        Intent intent = new Intent(context, WordMeaningDialog.class);
                        intent.putExtra("selectedtext", selectedText.toString());
                        intent.putExtra("verseId",  verseId);
                        intent.putExtra("versePage", versePage);
                        context.startActivity(intent);
                    }{
                    Toast.makeText(context, "الرجاء إختيار الكلمة بشكل دقيق. لا يمكن إظهار معنى لأكثر من كلمة", Toast.LENGTH_SHORT).show();
                }

            }
            return false;
        }

        public void onDestroyActionMode(ActionMode mode) {
        }

    }

}
