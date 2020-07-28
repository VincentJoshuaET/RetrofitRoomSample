package com.example.retrofitroomsample.paging

import androidx.paging.PagingSource
import com.example.retrofitroomsample.model.Item
import com.example.retrofitroomsample.repo.NetworkRepository

class ItemPagingSource(private val repo: NetworkRepository) : PagingSource<Int, Item>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> =
        try {
            val nextPage = params.key ?: 0
            val users = repo.getUsers(nextPage, params.loadSize)
            LoadResult.Page(
                data = users,
                prevKey = null,
                nextKey = nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

}