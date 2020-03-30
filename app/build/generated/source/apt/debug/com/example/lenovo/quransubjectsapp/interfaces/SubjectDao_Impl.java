package com.example.lenovo.quransubjectsapp.interfaces;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.SharedSQLiteStatement;
import android.database.Cursor;
import com.example.lenovo.quransubjectsapp.models.Subjects;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class SubjectDao_Impl implements SubjectDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfSubjects;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfSubjects;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public SubjectDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSubjects = new EntityInsertionAdapter<Subjects>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `subjects_table`(`serverId`,`ID`,`verseId`,`suraId`,`NoOfVerses`,`Subject`,`subject_type`,`Master`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Subjects value) {
        stmt.bindLong(1, value.getServerId());
        if (value.getID() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getID());
        }
        if (value.getVerseId() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getVerseId());
        }
        if (value.getSuraId() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getSuraId());
        }
        stmt.bindLong(5, value.getNoOfVerses());
        if (value.getSubject() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getSubject());
        }
        if (value.getSubject_type() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getSubject_type());
        }
        if (value.getMaster() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getMaster());
        }
      }
    };
    this.__deletionAdapterOfSubjects = new EntityDeletionOrUpdateAdapter<Subjects>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `subjects_table` WHERE `serverId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Subjects value) {
        stmt.bindLong(1, value.getServerId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM subjects_table";
        return _query;
      }
    };
  }

  @Override
  public void insert(Subjects subject) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfSubjects.insert(subject);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(Subjects s) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfSubjects.handle(s);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public List<Subjects> getAllSubjects() {
    final String _sql = "SELECT * from subjects_table ORDER BY serverId Desc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfServerId = _cursor.getColumnIndexOrThrow("serverId");
      final int _cursorIndexOfID = _cursor.getColumnIndexOrThrow("ID");
      final int _cursorIndexOfVerseId = _cursor.getColumnIndexOrThrow("verseId");
      final int _cursorIndexOfSuraId = _cursor.getColumnIndexOrThrow("suraId");
      final int _cursorIndexOfNoOfVerses = _cursor.getColumnIndexOrThrow("NoOfVerses");
      final int _cursorIndexOfSubject = _cursor.getColumnIndexOrThrow("Subject");
      final int _cursorIndexOfSubjectType = _cursor.getColumnIndexOrThrow("subject_type");
      final int _cursorIndexOfMaster = _cursor.getColumnIndexOrThrow("Master");
      final List<Subjects> _result = new ArrayList<Subjects>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Subjects _item;
        _item = new Subjects();
        final int _tmpServerId;
        _tmpServerId = _cursor.getInt(_cursorIndexOfServerId);
        _item.setServerId(_tmpServerId);
        final String _tmpID;
        _tmpID = _cursor.getString(_cursorIndexOfID);
        _item.setID(_tmpID);
        final String _tmpVerseId;
        _tmpVerseId = _cursor.getString(_cursorIndexOfVerseId);
        _item.setVerseId(_tmpVerseId);
        final String _tmpSuraId;
        _tmpSuraId = _cursor.getString(_cursorIndexOfSuraId);
        _item.setSuraId(_tmpSuraId);
        final int _tmpNoOfVerses;
        _tmpNoOfVerses = _cursor.getInt(_cursorIndexOfNoOfVerses);
        _item.setNoOfVerses(_tmpNoOfVerses);
        final String _tmpSubject;
        _tmpSubject = _cursor.getString(_cursorIndexOfSubject);
        _item.setSubject(_tmpSubject);
        final String _tmpSubject_type;
        _tmpSubject_type = _cursor.getString(_cursorIndexOfSubjectType);
        _item.setSubject_type(_tmpSubject_type);
        final String _tmpMaster;
        _tmpMaster = _cursor.getString(_cursorIndexOfMaster);
        _item.setMaster(_tmpMaster);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getNumberOfExistance(String subjectId) {
    final String _sql = "SELECT COUNT(*) FROM subjects_table WHERE ID =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (subjectId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, subjectId);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Subjects get(String subjectId) {
    final String _sql = "SELECT * FROM subjects_table WHERE ID =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (subjectId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, subjectId);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfServerId = _cursor.getColumnIndexOrThrow("serverId");
      final int _cursorIndexOfID = _cursor.getColumnIndexOrThrow("ID");
      final int _cursorIndexOfVerseId = _cursor.getColumnIndexOrThrow("verseId");
      final int _cursorIndexOfSuraId = _cursor.getColumnIndexOrThrow("suraId");
      final int _cursorIndexOfNoOfVerses = _cursor.getColumnIndexOrThrow("NoOfVerses");
      final int _cursorIndexOfSubject = _cursor.getColumnIndexOrThrow("Subject");
      final int _cursorIndexOfSubjectType = _cursor.getColumnIndexOrThrow("subject_type");
      final int _cursorIndexOfMaster = _cursor.getColumnIndexOrThrow("Master");
      final Subjects _result;
      if(_cursor.moveToFirst()) {
        _result = new Subjects();
        final int _tmpServerId;
        _tmpServerId = _cursor.getInt(_cursorIndexOfServerId);
        _result.setServerId(_tmpServerId);
        final String _tmpID;
        _tmpID = _cursor.getString(_cursorIndexOfID);
        _result.setID(_tmpID);
        final String _tmpVerseId;
        _tmpVerseId = _cursor.getString(_cursorIndexOfVerseId);
        _result.setVerseId(_tmpVerseId);
        final String _tmpSuraId;
        _tmpSuraId = _cursor.getString(_cursorIndexOfSuraId);
        _result.setSuraId(_tmpSuraId);
        final int _tmpNoOfVerses;
        _tmpNoOfVerses = _cursor.getInt(_cursorIndexOfNoOfVerses);
        _result.setNoOfVerses(_tmpNoOfVerses);
        final String _tmpSubject;
        _tmpSubject = _cursor.getString(_cursorIndexOfSubject);
        _result.setSubject(_tmpSubject);
        final String _tmpSubject_type;
        _tmpSubject_type = _cursor.getString(_cursorIndexOfSubjectType);
        _result.setSubject_type(_tmpSubject_type);
        final String _tmpMaster;
        _tmpMaster = _cursor.getString(_cursorIndexOfMaster);
        _result.setMaster(_tmpMaster);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
