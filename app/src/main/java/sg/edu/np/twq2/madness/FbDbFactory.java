package sg.edu.np.twq2.madness;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FbDbFactory {
    FirebaseDatabase fbDB;
    DatabaseReference dbRef;
    FbDbFactoryRead ref;

    interface FbDbFactoryRead {
        public void readReturns(String tag, DataSnapshot dataSnapshot);
        public void readFailure(String tag,DatabaseError error);
    }

    private void init()
    {
        //connection to database
        fbDB = FirebaseDatabase.getInstance();
        dbRef = fbDB.getReference();
    }
    private FbDbFactory() {
        init();
    }

    private FbDbFactory(FbDbFactoryRead ref)
    {
        init();
        this.ref = ref;
    }

    public static DatabaseReference getDbRef() {
        return new FbDbFactory().dbRef;
    }

    /**
     * Takes the parameters and chain them together
     * to reach the node before writing the data
     * e.g. dbRef.child("users").setValue(x);
     *
     * @param args  Array of parameters with the last as the value, and others as the child node
     */
    public static void writeData(String... args)
    {
        DatabaseReference tempDb = getDbRef();
        int i=0;
        for( ; i<args.length-1; i++)
        {
            tempDb = tempDb.child(args[i]);
        }
        tempDb.setValue(args[i]);
    }

    /**
     * Take an array of parameters and chain them together
     * to register a listener. Calls the interface when the value changed/first retrieved
     *
     * @param args  Array of parameters with the last as tag, and others as the child node
     */
    public void readData(final FbDbFactoryRead ref, final String... args)
    {
        DatabaseReference tempDb = getDbRef();
        int i=0;
        for( ; i<args.length-1; i++)
        {
            tempDb = tempDb.child(args[i]);
        }

        tempDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ref.readReturns(args[args.length-1], dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                ref.readFailure(args[args.length-1], databaseError);
            }
        });
    }

}
