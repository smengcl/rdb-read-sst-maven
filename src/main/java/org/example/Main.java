package org.example;

import org.rocksdb.Options;
import org.rocksdb.ReadOptions;
import org.rocksdb.RocksDBException;
import org.rocksdb.SstFileReader;
import org.rocksdb.SstFileReaderIterator;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Read all entries in an SST file.
 */
public class Main {
  public static void main(String[] args) throws RocksDBException {

    Options options = new Options();
    ReadOptions readOptions = new ReadOptions();
//    readOptions.setIgnoreRangeDeletions(false);

    try (SstFileReader currentFileReader = new SstFileReader(options)) {
      currentFileReader.open("000063.sst");

      try (SstFileReaderIterator currentFileIterator =
          currentFileReader.newIterator(readOptions)) {

        currentFileIterator.seekToFirst();

        // Does tombstone also pass isValid() ?
        while (currentFileIterator.isValid()) {
          final String key = new String(currentFileIterator.key(), UTF_8);
//          final String val = new String(currentFileIterator.value(), UTF_8);
          System.out.println("Key: " + key);

          currentFileIterator.next();
        }
      }
    }

  }
}
