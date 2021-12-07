package pt.ulusofona.aed.deisiRockstar2021;

public class ParseInfo {
    int numeroLinhasOk;
    int numeroLinhasIgnoradas;

    public ParseInfo(int numeroLinhasOk, int numeroLinhasIgnoradas) {
        this.numeroLinhasOk = numeroLinhasOk;
        this.numeroLinhasIgnoradas = numeroLinhasIgnoradas;
    }

    public ParseInfo() {}

    @Override
    public String toString() {
        return "OK: " + numeroLinhasOk + ", Ignored: " + numeroLinhasIgnoradas;
    }
}
