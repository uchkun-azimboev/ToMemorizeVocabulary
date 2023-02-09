package uz.pdp.tomemorizevocabulary.ui.word

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.pdp.tomemorizevocabulary.databinding.ItemViewPhotoBinding
import uz.pdp.tomemorizevocabulary.model.photos.Photo

class PhotoAdapter : ListAdapter<Photo, PhotoAdapter.PhotoViewHolder>(PhotoDiffCallBack()) {

    inner class PhotoViewHolder(private val binding: ItemViewPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ResourceType")
        fun bind(photo: Photo) = binding.apply {
            Glide.with(root.context).load(photo.src.medium)
                .placeholder(Color.parseColor(photo.avg_color)).into(ivPhoto)
        }
    }

    private class PhotoDiffCallBack : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
        PhotoViewHolder(
            ItemViewPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}