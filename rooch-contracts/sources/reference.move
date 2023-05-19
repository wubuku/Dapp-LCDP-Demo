// <autogenerated>
//   This file was generated by dddappp code generator.
//   Any changes made to this file manually will be lost next time the file is regenerated.
// </autogenerated>

module rooch_demo::reference {
    use std::option::Option;
    use std::string::String;
    friend rooch_demo::article_create_logic;
    friend rooch_demo::article_add_reference_logic;
    friend rooch_demo::article_update_reference_logic;
    friend rooch_demo::article;

    const EID_DATA_TOO_LONG: u64 = 102;

    struct Reference has store {
        reference_number: u64,
        title: String,
        author: String,
        publication_year: Option<u64>,
        publisher: Option<String>,
        doi: Option<String>,
        url: Option<String>,
        page_number: Option<u64>,
    }

    public fun reference_number(reference: &Reference): u64 {
        reference.reference_number
    }

    public fun title(reference: &Reference): String {
        reference.title
    }

    public(friend) fun set_title(reference: &mut Reference, title: String) {
        reference.title = title;
    }

    public fun author(reference: &Reference): String {
        reference.author
    }

    public(friend) fun set_author(reference: &mut Reference, author: String) {
        reference.author = author;
    }

    public fun publication_year(reference: &Reference): Option<u64> {
        reference.publication_year
    }

    public(friend) fun set_publication_year(reference: &mut Reference, publication_year: Option<u64>) {
        reference.publication_year = publication_year;
    }

    public fun publisher(reference: &Reference): Option<String> {
        reference.publisher
    }

    public(friend) fun set_publisher(reference: &mut Reference, publisher: Option<String>) {
        reference.publisher = publisher;
    }

    public fun doi(reference: &Reference): Option<String> {
        reference.doi
    }

    public(friend) fun set_doi(reference: &mut Reference, doi: Option<String>) {
        reference.doi = doi;
    }

    public fun url(reference: &Reference): Option<String> {
        reference.url
    }

    public(friend) fun set_url(reference: &mut Reference, url: Option<String>) {
        reference.url = url;
    }

    public fun page_number(reference: &Reference): Option<u64> {
        reference.page_number
    }

    public(friend) fun set_page_number(reference: &mut Reference, page_number: Option<u64>) {
        reference.page_number = page_number;
    }

    public(friend) fun new_reference(
        reference_number: u64,
        title: String,
        author: String,
        publication_year: Option<u64>,
        publisher: Option<String>,
        doi: Option<String>,
        url: Option<String>,
        page_number: Option<u64>,
    ): Reference {
        Reference {
            reference_number,
            title,
            author,
            publication_year,
            publisher,
            doi,
            url,
            page_number,
        }
    }

    public(friend) fun drop_reference(reference: Reference) {
        let Reference {
            reference_number: _,
            title: _,
            author: _,
            publication_year: _,
            publisher: _,
            doi: _,
            url: _,
            page_number: _,
        } = reference;
    }


}
