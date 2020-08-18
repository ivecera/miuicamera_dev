package com.bumptech.glide.load.a;

import android.content.ContentResolver;
import android.content.UriMatcher;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: StreamLocalUriFetcher */
public class o extends m<InputStream> {
    private static final int Pl = 1;
    private static final int Ql = 2;
    private static final int Rl = 3;
    private static final int Sl = 4;
    private static final int Tl = 5;
    private static final UriMatcher Ul = new UriMatcher(-1);

    static {
        Ul.addURI("com.android.contacts", "contacts/lookup/*/#", 1);
        Ul.addURI("com.android.contacts", "contacts/lookup/*", 1);
        Ul.addURI("com.android.contacts", "contacts/#/photo", 2);
        Ul.addURI("com.android.contacts", "contacts/#", 3);
        Ul.addURI("com.android.contacts", "contacts/#/display_photo", 4);
        Ul.addURI("com.android.contacts", "phone_lookup/*", 5);
    }

    public o(ContentResolver contentResolver, Uri uri) {
        super(contentResolver, uri);
    }

    private InputStream b(Uri uri, ContentResolver contentResolver) throws FileNotFoundException {
        int match = Ul.match(uri);
        if (match != 1) {
            if (match == 3) {
                return openContactPhotoInputStream(contentResolver, uri);
            }
            if (match != 5) {
                return contentResolver.openInputStream(uri);
            }
        }
        Uri lookupContact = ContactsContract.Contacts.lookupContact(contentResolver, uri);
        if (lookupContact != null) {
            return openContactPhotoInputStream(contentResolver, lookupContact);
        }
        throw new FileNotFoundException("Contact cannot be found");
    }

    private InputStream openContactPhotoInputStream(ContentResolver contentResolver, Uri uri) {
        return ContactsContract.Contacts.openContactPhotoInputStream(contentResolver, uri, true);
    }

    /* access modifiers changed from: protected */
    @Override // com.bumptech.glide.load.a.m
    public InputStream a(Uri uri, ContentResolver contentResolver) throws FileNotFoundException {
        InputStream b2 = b(uri, contentResolver);
        if (b2 != null) {
            return b2;
        }
        throw new FileNotFoundException("InputStream is null for " + uri);
    }

    /* access modifiers changed from: protected */
    /* renamed from: f */
    public void e(InputStream inputStream) throws IOException {
        inputStream.close();
    }

    @Override // com.bumptech.glide.load.a.d
    @NonNull
    public Class<InputStream> ga() {
        return InputStream.class;
    }
}
