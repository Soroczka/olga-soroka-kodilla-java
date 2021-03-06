package com.kodilla.testing.library;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class BookDirectoryTestSuite {

    @Test
    public void testListBooksWithConditionsReturnList(){

        //Given
        LibraryDatabase libraryDatabaseMock = mock(LibraryDatabase.class);
        BookLibrary bookLibrary = new BookLibrary(libraryDatabaseMock);
        List<Book> resultListOfBooks = new ArrayList<>();
        Book book1 = new Book("Secrets of Alamo", "John Smith", 2008);
        Book book2 = new Book("Secretaries and Directors", "Dilbert Michigan", 2012);
        Book book3 = new Book("Secret life of programmers", "Steve Wolkowitz", 2016);
        Book book4 = new Book("Secrets of Java", "Ian Tenewitch", 2010);
        resultListOfBooks.add(book1);
        resultListOfBooks.add(book2);
        resultListOfBooks.add(book3);
        resultListOfBooks.add(book4);
        when(libraryDatabaseMock.listBooksWithCondition("Secret")).thenReturn(resultListOfBooks);

        //When
        List<Book> theListOfBooks = bookLibrary.listBookWithCondition("Secret");

        //Then

        assertEquals(4, theListOfBooks.size()); //czy lista jest poprawnie zwracana
    }

    @Test
    public void testListBooksWithConditionsMoreThan20(){
        //Given
        LibraryDatabase libraryDatabaseMock = mock(LibraryDatabase.class);
        BookLibrary bookLibrary = new BookLibrary(libraryDatabaseMock);
        List<Book> resultListOf0Books = new ArrayList<>();
        List<Book> resultListOf15Books = generateListOfBooks(15);
        List<Book> resultListOf40Books = generateListOfBooks(40);
        when(libraryDatabaseMock.listBooksWithCondition(anyString())).thenReturn(resultListOf15Books);
        when(libraryDatabaseMock.listBooksWithCondition("ZeroBooks")).thenReturn(resultListOf0Books);
        when(libraryDatabaseMock.listBooksWithCondition("FourtyBooks")).thenReturn(resultListOf40Books);

        //When
        List<Book> theListOfBook0 = bookLibrary.listBookWithCondition("ZeroBooks");
        List<Book> theListOfBook15 = bookLibrary.listBookWithCondition("Any title");
        List<Book> theListOfBooks40 = bookLibrary.listBookWithCondition("FourtyBooks");

        //Then

        assertEquals(0, theListOfBook0.size());
        assertEquals(15, theListOfBook15.size());
        assertEquals(0, theListOfBooks40.size());

    }

    @Test
    public void testListBooksWithConditionFragmentShorterThan3(){
        //Given
        LibraryDatabase libraryDatabaseMock = mock(LibraryDatabase.class);
        BookLibrary bookLibrary = new BookLibrary(libraryDatabaseMock);
        List<Book> resultListOf10Books = generateListOfBooks(10);
        when(libraryDatabaseMock.listBooksWithCondition(anyString())).thenReturn(resultListOf10Books);

        //When
        List<Book> theListOfBooks10 = bookLibrary.listBookWithCondition("An");

        //Then
        assertEquals(0, theListOfBooks10.size()); //spr czy zwracana lista ksiazek jest pusta w sytuacji gdy framgent tytulu krotszy niz 3 znaki
        verify(libraryDatabaseMock, times(0)).listBooksWithCondition(anyString());
    }


    @Test
    public void testListBooksInHandsWhenBorrowedZero(){
        //Given
        LibraryDatabase libraryDatabaseMock = mock(LibraryDatabase.class);
        BookLibrary bookLibrary = new BookLibrary(libraryDatabaseMock);
        List<Book> resultListOfBooks = new ArrayList<>();
        LibraryUser user = new LibraryUser("Jan", "Kowalski", "9876543210");
        when(libraryDatabaseMock.listBooksInHandsOf(user)).thenReturn(resultListOfBooks);

        //When
        List<Book> theListOfBooks = bookLibrary.listBooksInHandsOf(user);

        //Then
        assertEquals(0, theListOfBooks.size());
        verify(libraryDatabaseMock,times(1)).listBooksInHandsOf(user);

    }

    @Test
    public void testListBooksInHandsWhenBorrowedOne(){
        //Given
        LibraryDatabase libraryDatabaseMock = mock(LibraryDatabase.class);
        BookLibrary bookLibrary = new BookLibrary(libraryDatabaseMock);
        List<Book> resultListOfBooks = new ArrayList<>();
        Book book1 = new Book("Secrets of Alamo", "John Smith", 2008);
        resultListOfBooks.add(book1);
        LibraryUser user = new LibraryUser("Jan", "Kowalski", "9876543210");
        when(libraryDatabaseMock.listBooksInHandsOf(user)).thenReturn(resultListOfBooks);

        //When
        List<Book> theListOfBooks = bookLibrary.listBooksInHandsOf(user);

        //Then
        assertEquals(1, theListOfBooks.size());
        verify(libraryDatabaseMock,times(1)).listBooksInHandsOf(user);

    }

    @Test
    public void testListBooksInHandsWhenBorrowedFive(){
        //Given
        LibraryDatabase libraryDatabaseMock = mock(LibraryDatabase.class);

        BookLibrary bookLibrary = new BookLibrary(libraryDatabaseMock);
        List<Book> resultListOfBooks = new ArrayList<>();
        Book book1 = new Book("Secrets of Alamo", "John Smith", 2008);
        Book book2 = new Book("Secretaries and Directors", "Dilbert Michigan", 2012);
        Book book3 = new Book("Secret life of programmers", "Steve Wolkowitz", 2016);
        Book book4 = new Book("Secrets of Java", "Ian Tenewitch", 2010);
        Book book5 = new Book("Clean code", "Robert C. Martin", 2009);
        resultListOfBooks.add(book1);
        resultListOfBooks.add(book2);
        resultListOfBooks.add(book3);
        resultListOfBooks.add(book4);
        resultListOfBooks.add(book5);

        LibraryUser user = new LibraryUser("Jan", "Kowalski", "9876543210");

        Map<LibraryUser, List<Book>> borrowedBooksByUser = new HashMap<>();
        borrowedBooksByUser.put(user, resultListOfBooks);

        when(libraryDatabaseMock.listBooksInHandsOf(user)).thenReturn(resultListOfBooks);

        //When
        List<Book> theListOfBooks = bookLibrary.listBooksInHandsOf(user);

        //Then
        assertEquals(5, theListOfBooks.size());
        verify(libraryDatabaseMock,times(1)).listBooksInHandsOf(user);

    }


    private List<Book> generateListOfBooks(int booksQuantity){
        List<Book> resultList = new ArrayList<>();
        for(int n = 1; n <= booksQuantity; n++){
            Book theBook = new Book ("Title " +n ,"Author "+n, 1970 + n);
            resultList.add(theBook);
        }
        return resultList;
    }



}



