package com.marcio.popularmoviesclean

import com.marcio.popularmoviesclean.movies.models.Movie

class TestData {

    companion object {

        val MOVIES = listOf(
            Movie(
                "Suicide Squad",
                "/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg",
                "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
                5.91,
                1466,
                "2016-08-03"
            ),
            Movie(
                "Jason Bourne",
                "/lFSSLTlFozwpaGlO31OoUeirBgQ.jpg",
                "The most dangerous former operative of the CIA is drawn out of hiding to uncover hidden truths about his past.",
                5.25,
                649,
                "2016-07-27"
            )
        )

        const val JSON_MOVIES_RESPONSE = """
            {
              "page": 1,
              "results": [
                {
                  "poster_path": "/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg",
                  "adult": false,
                  "overview": "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
                  "release_date": "2016-08-03",
                  "genre_ids": [
                    14,
                    28,
                    80
                  ],
                  "id": 297761,
                  "original_title": "Suicide Squad",
                  "original_language": "en",
                  "title": "Suicide Squad",
                  "backdrop_path": "/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg",
                  "popularity": 48.261451,
                  "vote_count": 1466,
                  "video": false,
                  "vote_average": 5.91
                },
                {
                  "poster_path": "/lFSSLTlFozwpaGlO31OoUeirBgQ.jpg",
                  "adult": false,
                  "overview": "The most dangerous former operative of the CIA is drawn out of hiding to uncover hidden truths about his past.",
                  "release_date": "2016-07-27",
                  "genre_ids": [
                    28,
                    53
                  ],
                  "id": 324668,
                  "original_title": "Jason Bourne",
                  "original_language": "en",
                  "title": "Jason Bourne",
                  "backdrop_path": "/AoT2YrJUJlg5vKE3iMOLvHlTd3m.jpg",
                  "popularity": 30.690177,
                  "vote_count": 649,
                  "video": false,
                  "vote_average": 5.25
                }                            
              ],
              "total_results": 2,
              "total_pages": 1
            }
        """
    }
}
