package com.ramzel.giphy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.ramzel.giphy.models.Datum;
import com.ramzel.giphy.models.Giph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class SearchPresenter {

    @NonNull private RecyclerView.Adapter adapter = new SearchAdapter();
    @NonNull private List<Datum> data = new ArrayList<>();
    @NonNull private Listener listener;
    private int columnWidth;

    SearchPresenter(@NonNull View view, @NonNull Listener listener, int columnWidth) {
        this.listener = listener;
        this.columnWidth = columnWidth;

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
    }

    void addGiphs(@NonNull Collection<Datum> data) {
        this.data.addAll(data);
        adapter.notifyItemRangeChanged(this.data.size() - data.size(), data.size());
    }

    void clear() {
        int size = data.size();
        data.clear();
        adapter.notifyItemRangeRemoved(0, size);
    }

    void setSearchView(@NonNull SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listener.queryChanged(newText);
                clear();
                return false;
            }
        });

        searchView.setIconified(false);
        searchView.requestFocus();
    }

    private class SearchAdapter extends RecyclerView.Adapter<GiphViewHolder> {

        @Override
        public GiphViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GiphViewHolder(parent.getContext(), parent);
        }

        @Override
        public void onBindViewHolder(final GiphViewHolder holder, final int position) {
            final Datum datum = data.get(position);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { listener.giphSelected(datum); }
            });

            holder.loadGiph(datum, columnWidth);

            //Load more if we are on the last one
            if (position == data.size() - 1) {
                listener.wantMoreGiphs(data.size());
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

    }

    private static class GiphViewHolder extends RecyclerView.ViewHolder {

        @NonNull private final ImageView imageView;
        @NonNull private final ProgressBar progressView;

        GiphViewHolder(@NonNull Context context, @NonNull ViewGroup parent) {
            super(LayoutInflater.from(context).inflate(R.layout.item_giph, parent, false));
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
            progressView = (ProgressBar) itemView.findViewById(R.id.progress);
        }

        private void loadGiph(@NonNull Datum datum, int columnWidth) {
            if (datum.images != null) {
                Giph giph = datum.images.fixed_width;
                if (giph != null) {
                    //Use the column width to size the viewholder before the image is loaded
                    itemView.getLayoutParams().height = giph.height * columnWidth /giph.width;
                    itemView.requestLayout();

                    progressView.setVisibility(View.VISIBLE);
                    GlideDrawableImageViewTarget imageViewTarget =
                            new GlideDrawableImageViewTarget(this.imageView){
                                @Override
                                public void onResourceReady(
                                        GlideDrawable resource,
                                        GlideAnimation<? super GlideDrawable> animation) {
                                    super.onResourceReady(resource, animation);
                                    progressView.setVisibility(View.INVISIBLE);
                                }
                            };
                    Glide.with(itemView.getContext()).load(giph.url).into(imageViewTarget);
                }
            }
        }
    }

    interface Listener {

        void wantMoreGiphs(int offset);

        void giphSelected(@NonNull Datum datum);

        void queryChanged(@NonNull String query);
    }
}
