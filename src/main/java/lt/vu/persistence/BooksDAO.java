package lt.vu.persistence;

import lt.vu.entities.Book;
import lt.vu.entities.User;
import lt.vu.rest.contracts.BooksDto;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.*;

@ApplicationScoped
public class BooksDAO {
     @Inject
    private EntityManager em;

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public void persist(Book book){
        this.em.persist(book);
    }

    public Book findOne(String isbn) {
        return em.find(Book.class, isbn);
    }

    public List<Book> loadAll() {
        return em.createNamedQuery("Book.findAll", Book.class).getResultList();
    }
    public List<Book> loadAllNonTaken() {
        return em.createNamedQuery("Book.findAllNonTaken", Book.class).getResultList();
    }
    public List<Book> loadAllByAuthor(String author) {
        return em.createNamedQuery("Book.findAllbyAuthor", Book.class).setParameter("author", author).getResultList();
    }

    public void flush(){
        this.em.flush();
    }

    public List<BooksDto> loadAllgrouped() {
        HashMap<Integer, List<Book>> hashMap = loadAllgrouped_hashmaped();
        List<BooksDto> booksDtos= new ArrayList<>();
        for (Map.Entry<Integer,List<Book>> entry: hashMap.entrySet()
             ) {
            System.out.println(entry.getValue().size());
            for (Book book:entry.getValue()) {
                BooksDto booksDto = new BooksDto(entry.getKey(),book.getAuthor(),book.getTitle(),entry.getValue().size());
                booksDtos.add(booksDto);
                break;
            }
        }
        return booksDtos;
    }

    public HashMap<Integer,List<Book>> loadAllgrouped_hashmaped() {
        List<Book> books= loadAllNonTaken();
        if(books == null || books.size()==0) return null;
        HashMap<Integer,List<Book>> hashMap= new HashMap<>();
        for (Book book:books) {
            Integer key= book.hashCode();
            List<Book> bookList=hashMap.get(key);
            if (bookList == null) {
                bookList=new ArrayList<>();
                bookList.add(book);
                hashMap.put(key,bookList);
            } else {
                bookList.add(book);
            }
        }
        return hashMap;
    }
    public Book loadByhash(Integer hash) {
        HashMap<Integer, List<Book>> hashMap = loadAllgrouped_hashmaped();
        if(hashMap.get(hash)==null || hashMap.get(hash).isEmpty())return null;
        return hashMap.get(hash).get(0);
    }
    public Book update(Book book){
        return em.merge(book);
    }

}
