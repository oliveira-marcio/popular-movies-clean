package com.marcio.popularmoviesclean

import com.marcio.popularmoviesclean.movies.models.Movie

class TestData {

    companion object {

        val POPULAR_MOVIES = listOf(
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

        val TOP_RATED_MOVIES = listOf(
            Movie(
                "The Shawshank Redemption",
                "/9O7gLzmreU0nGkIB6K3BsJbzvNv.jpg",
                "Framed in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.",
                8.32,
                5238,
                "1994-09-10"
            ),
            Movie(
                "Whiplash",
                "/lIv1QinFqz4dlp5U4lQ6HaiskOZ.jpg",
                "Under the direction of a ruthless instructor, a talented young drummer begins to pursue perfection at any cost, even his humanity.",
                8.29,
                2059,
                "2014-10-10"
            )
        )

        const val JSON_POPULAR_MOVIES_RESPONSE = """
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

        const val JSON_TOP_RATED_MOVIES_RESPONSE = """
            {
              "page": 1,
              "results": [
                {
                  "poster_path": "/9O7gLzmreU0nGkIB6K3BsJbzvNv.jpg",
                  "adult": false,
                  "overview": "Framed in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.",
                  "release_date": "1994-09-10",
                  "genre_ids": [
                    18,
                    80
                  ],
                  "id": 278,
                  "original_title": "The Shawshank Redemption",
                  "original_language": "en",
                  "title": "The Shawshank Redemption",
                  "backdrop_path": "/xBKGJQsAIeweesB79KC89FpBrVr.jpg",
                  "popularity": 6.741296,
                  "vote_count": 5238,
                  "video": false,
                  "vote_average": 8.32
                },
                {
                  "poster_path": "/lIv1QinFqz4dlp5U4lQ6HaiskOZ.jpg",
                  "adult": false,
                  "overview": "Under the direction of a ruthless instructor, a talented young drummer begins to pursue perfection at any cost, even his humanity.",
                  "release_date": "2014-10-10",
                  "genre_ids": [
                    18,
                    10402
                  ],
                  "id": 244786,
                  "original_title": "Whiplash",
                  "original_language": "en",
                  "title": "Whiplash",
                  "backdrop_path": "/6bbZ6XyvgfjhQwbplnUh1LSj1ky.jpg",
                  "popularity": 10.776056,
                  "vote_count": 2059,
                  "video": false,
                  "vote_average": 8.29
                }            
              ],
              "total_results": 2,
              "total_pages": 1
            }
        """
    }
}
