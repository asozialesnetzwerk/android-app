package com.github.doomsdayrs.apps.shosetsu.ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.Doomsdayrs.api.novelreader_core.services.core.dep.Formatter;
import com.github.doomsdayrs.apps.shosetsu.R;
import com.github.doomsdayrs.apps.shosetsu.backend.database.Database;
import com.github.doomsdayrs.apps.shosetsu.ui.novel.NovelFragment;
import com.github.doomsdayrs.apps.shosetsu.ui.novel.StaticNovel;
import com.github.doomsdayrs.apps.shosetsu.variables.DefaultScrapers;
import com.github.doomsdayrs.apps.shosetsu.variables.recycleObjects.NovelCard;
import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import graphql.language.StringValue;

/*
 * This file is part of Shosetsu.
 * Shosetsu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with Shosetsu.  If not, see https://www.gnu.org/licenses/ .
 * ====================================================================
 * Shosetsu
 * 9 / June / 2019
 *
 * @author github.com/doomsdayrs
 */
public class LibraryNovelCardsAdapter extends RecyclerView.Adapter<LibraryNovelCardsAdapter.NovelCardsViewHolder> {
    private final ArrayList<NovelCard> recycleCards;
    private final FragmentManager fragmentManager;

    public LibraryNovelCardsAdapter(ArrayList<NovelCard> recycleCards, FragmentManager fragmentManager) {
        this.recycleCards = recycleCards;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public NovelCardsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_novel_card, viewGroup, false);
        return new NovelCardsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NovelCardsViewHolder novelCardsViewHolder, int i) {
        NovelCard recycleCard = recycleCards.get(i);
        Log.d("ImageURL LOAD", recycleCard.imageURL);
        Picasso.get()
                .load(recycleCard.imageURL)
                .into(novelCardsViewHolder.library_card_image);
        novelCardsViewHolder.fragmentManager = fragmentManager;
        novelCardsViewHolder.novelURL = recycleCard.novelURL;
        novelCardsViewHolder.setFormatter(DefaultScrapers.formatters.get(recycleCard.formatterID - 1));
        novelCardsViewHolder.library_card_title.setText(recycleCard.title);
        int count = Database.DatabaseChapter.getCountOfChaptersUnread(recycleCard.novelURL);
        if (count != 0) {
            novelCardsViewHolder.chip.setVisibility(View.VISIBLE);
            novelCardsViewHolder.chip.setText(String.valueOf(count));
        } else novelCardsViewHolder.chip.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return recycleCards.size();
    }

    static class NovelCardsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView library_card_image;
        final TextView library_card_title;
        final Chip chip;

        Formatter formatter;
        FragmentManager fragmentManager;
        String novelURL;

        NovelCardsViewHolder(@NonNull View itemView) {
            super(itemView);
            library_card_image = itemView.findViewById(R.id.novel_item_image);
            library_card_title = itemView.findViewById(R.id.textView);
            chip = itemView.findViewById(R.id.novel_item_left_to_read);

            itemView.setOnClickListener(this);
        }

        void setFormatter(Formatter formatter) {
            this.formatter = formatter;
        }

        @Override
        public void onClick(View v) {
            NovelFragment novelFragment = new NovelFragment();
            StaticNovel.formatter = formatter;
            StaticNovel.novelURL = novelURL;
            novelFragment.fragmentManager = fragmentManager;
            fragmentManager.beginTransaction()
                    .addToBackStack("tag")
                    .replace(R.id.fragment_container, novelFragment)
                    .commit();
        }
    }
}
