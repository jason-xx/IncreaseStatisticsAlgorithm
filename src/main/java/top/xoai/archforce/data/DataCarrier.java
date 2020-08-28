package top.xoai.archforce.data;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author wx
 * @version 1.0
 * @date 2020/8/18 9:28 上午
 */
public class DataCarrier {

    private LinkedBlockingQueue<ItemTable> cache = new LinkedBlockingQueue();

    public void push(ItemTable data){
        try{
            this.cache.put(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ItemTable take(){
        ItemTable itemTable = null;
        try {
            itemTable = this.cache.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return itemTable;
    }

    public void clean(){
        this.cache.clear();
    }

    public int size(){
        return this.cache.size();
    }

    public ItemTable peek(){
        return this.cache.peek();
    }

    public ItemTable poll(){
        return this.cache.poll();
    }
}
