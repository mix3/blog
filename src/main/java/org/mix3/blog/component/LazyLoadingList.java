package org.mix3.blog.component;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.List;

@SuppressWarnings("serial")
public abstract class LazyLoadingList <E> extends AbstractList <E> implements Serializable{
	protected int cacheStart = -1;
	protected int cacheEnd = 0;
	protected List<E> cache = null;
	@Override
	public E get(int index) {
		if(cache == null || index < cacheStart || cacheEnd < index){
			try {
				cache = getPage(index, getCacheSize());
				cacheStart = index;
				cacheEnd = index + cache.size() - 1;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		return cache.get(index-cacheStart);
	}
	private int getCacheSize(){
		return 20;
	}
    abstract protected List<E> getPage(int start,int size) throws SQLException, IOException;
}
