package com.example.lenovo.quransubjectsapp.adapters;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import com.example.lenovo.quransubjectsapp.interfaces.SubjectDao;
import com.example.lenovo.quransubjectsapp.interfaces.SubjectDao_Impl;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class SubjectDatabase_Impl extends SubjectDatabase {
  private volatile SubjectDao _subjectDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `subjects_table` (`serverId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `ID` TEXT, `verseId` TEXT, `suraId` TEXT, `NoOfVerses` INTEGER NOT NULL, `Subject` TEXT, `subject_type` TEXT, `Master` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"a7a3fec76b37d86955364d9a80d17ffc\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `subjects_table`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsSubjectsTable = new HashMap<String, TableInfo.Column>(8);
        _columnsSubjectsTable.put("serverId", new TableInfo.Column("serverId", "INTEGER", true, 1));
        _columnsSubjectsTable.put("ID", new TableInfo.Column("ID", "TEXT", false, 0));
        _columnsSubjectsTable.put("verseId", new TableInfo.Column("verseId", "TEXT", false, 0));
        _columnsSubjectsTable.put("suraId", new TableInfo.Column("suraId", "TEXT", false, 0));
        _columnsSubjectsTable.put("NoOfVerses", new TableInfo.Column("NoOfVerses", "INTEGER", true, 0));
        _columnsSubjectsTable.put("Subject", new TableInfo.Column("Subject", "TEXT", false, 0));
        _columnsSubjectsTable.put("subject_type", new TableInfo.Column("subject_type", "TEXT", false, 0));
        _columnsSubjectsTable.put("Master", new TableInfo.Column("Master", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSubjectsTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSubjectsTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSubjectsTable = new TableInfo("subjects_table", _columnsSubjectsTable, _foreignKeysSubjectsTable, _indicesSubjectsTable);
        final TableInfo _existingSubjectsTable = TableInfo.read(_db, "subjects_table");
        if (! _infoSubjectsTable.equals(_existingSubjectsTable)) {
          throw new IllegalStateException("Migration didn't properly handle subjects_table(com.example.lenovo.quransubjectsapp.models.Subjects).\n"
                  + " Expected:\n" + _infoSubjectsTable + "\n"
                  + " Found:\n" + _existingSubjectsTable);
        }
      }
    }, "a7a3fec76b37d86955364d9a80d17ffc", "84b358c31f857e59f40fec6cd7e614c0");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "subjects_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `subjects_table`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public SubjectDao subjectDao() {
    if (_subjectDao != null) {
      return _subjectDao;
    } else {
      synchronized(this) {
        if(_subjectDao == null) {
          _subjectDao = new SubjectDao_Impl(this);
        }
        return _subjectDao;
      }
    }
  }
}
