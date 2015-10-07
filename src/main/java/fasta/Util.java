package fasta;

import distance.HammingDistance;
import hashing.JenkinsHash;
import org.jcvi.jillion.core.datastore.DataStoreException;
import org.jcvi.jillion.core.datastore.DataStoreProviderHint;
import org.jcvi.jillion.core.util.iter.StreamingIterator;
import org.jcvi.jillion.fasta.nt.NucleotideFastaDataStore;
import org.jcvi.jillion.fasta.nt.NucleotideFastaFileDataStoreBuilder;
import org.jcvi.jillion.fasta.nt.NucleotideFastaRecord;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hd on 10/6/15.
 */
public class Util {

    private static final JenkinsHash HASHING = new JenkinsHash();
    private static final HammingDistance HAMMING_DISTANCE = new HammingDistance();

    public static List<ReadItem> readToHash(File fastaFile, int lastIndexOfReadId) {
        List<ReadItem> result = new ArrayList<>();

        try {
            NucleotideFastaDataStore datastore = new  NucleotideFastaFileDataStoreBuilder(fastaFile)
                    .hint(DataStoreProviderHint.ITERATION_ONLY)
                    .build();
            StreamingIterator<NucleotideFastaRecord> itr = datastore.iterator();

            while (itr.hasNext()) {
                NucleotideFastaRecord r = itr.next();
                if (r.getId().length() > lastIndexOfReadId)
                    result.add(new ReadItem(HASHING.hash32(r.getSequence().toString().getBytes()),
                            r.getId().substring(0, lastIndexOfReadId), r.getSequence()));
                else
                    result.add(new ReadItem(HASHING.hash32(r.getSequence().toString().getBytes()),
                            r.getId(), r.getSequence()));

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DataStoreException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static int[][] calcDistanceMatrix(List<ReadItem> readItems) {
        int[][] result = new int[readItems.size()][readItems.size()];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result.length; j++) {
                result[i][j] = HAMMING_DISTANCE.
                        getDistance(Integer.toHexString(readItems.get(i).getHash()),
                                Integer.toHexString(readItems.get(j).getHash()));
            }
        }
        return result;
    }
}
