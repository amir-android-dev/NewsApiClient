package com.amir.newsapiclient.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amir.newsapiclient.data.model.Article
import com.amir.newsapiclient.databinding.NewsListItemBinding
import com.bumptech.glide.Glide

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    //diffUtil
    private val callback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            /*
            DiffUtil uses this areItemsTheSame function to decide whether two objects represent the same Item in the old and new list.
             We can use value of one property for this comparison. Here we take the url.
             */
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            //Diffutil uses this areContentsTheSame function two decide whether two objects have the same data
            return oldItem == newItem
        }

    }

    //to get the AsyncListDiffer
    val differ = AsyncListDiffer(this, callback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    //using viewBinding
    inner class NewsViewHolder(private val binding: NewsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            Log.i("MYTAG", "came here ${article.title}")
            binding.tvTitle.text = article.title
            binding.tvDescription.text = article.description
            binding.tvPublishedAt.text = article.publishedAt
            binding.tvSource.text = article.source.name

            Glide.with(binding.ivArticleImage.context).load(article.urlToImage)
                .into(binding.ivArticleImage)
             //3
            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(article)
                }
            }
        }
    }
//1
    private var onItemClickListener: ((Article) -> Unit)? = null
//2
    fun setOnItemClickListener(listener : (Article)->Unit){
        onItemClickListener = listener
    }

}