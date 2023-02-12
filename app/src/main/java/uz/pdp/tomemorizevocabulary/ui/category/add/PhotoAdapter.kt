package uz.pdp.tomemorizevocabulary.ui.category.add

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.pdp.tomemorizevocabulary.databinding.ItemViewPhotoBinding
import uz.pdp.tomemorizevocabulary.model.photos.Photo
import uz.pdp.tomemorizevocabulary.model.photos.ThreePhoto
import uz.pdp.tomemorizevocabulary.utils.Extensions.animatedClick
import uz.pdp.tomemorizevocabulary.utils.Extensions.setup

class PhotoAdapter :
    ListAdapter<ThreePhoto, PhotoAdapter.PhotoViewHolder>(PhotoDiffCallBack()) {

    var itemClick: ((Photo) -> Unit)? = null

    inner class PhotoViewHolder(private val binding: ItemViewPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("ResourceType")
        fun bind(threePhoto: ThreePhoto) = binding.apply {

            Glide.with(root.context).setup(threePhoto.photo1, ivPhoto1)

            threePhoto.photo2?.let {
                Glide.with(root.context).setup(it, ivPhoto2)
            }

            threePhoto.photo3?.let {
                Glide.with(root.context).setup(it, ivPhoto3)
            }

            ivPhoto1 animatedClick { itemClick?.invoke(threePhoto.photo1) }
            ivPhoto2 animatedClick { itemClick?.invoke(threePhoto.photo2!!) }
            ivPhoto3 animatedClick { itemClick?.invoke(threePhoto.photo3!!) }
        }
    }


    private class PhotoDiffCallBack : DiffUtil.ItemCallback<ThreePhoto>() {
        override fun areItemsTheSame(oldItem: ThreePhoto, newItem: ThreePhoto): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: ThreePhoto, newItem: ThreePhoto): Boolean =
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