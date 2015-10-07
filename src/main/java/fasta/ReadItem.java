package fasta;

import org.jcvi.jillion.core.residue.nt.NucleotideSequence;

/**
 * Created by hd on 10/6/15.
 */
public class ReadItem {
    private int hash;
    private String Name;
    private NucleotideSequence sequence;

    public ReadItem(int hash, String name, NucleotideSequence sequence) {
        this.hash = hash;
        Name = name;
        this.sequence = sequence;
    }

    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public NucleotideSequence getSequence() {
        return sequence;
    }

    public void setSequence(NucleotideSequence sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return "ReadItem{" +
                "hash=" + hash +
                ", Name='" + Name + '\'' +
                ", sequence=" + sequence +
                '}';
    }
}
