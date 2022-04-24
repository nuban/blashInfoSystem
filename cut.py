# coding=utf-8
from textrank4zh import TextRank4Keyword, TextRank4Sentence
import jieba.analyse


'''def keywords_extraction(text):
    tr4w = TextRank4Keyword(allow_speech_tags=['n', 'nr', 'ns', 'nt', 'nz'])
    tr4w.analyze(text=text, window=2, lower=True, vertex_source='all_filters', edge_source='no_stop_words',
                 pagerank_config={'alpha': 0.85, })
    keywords = tr4w.get_keywords(num=6, word_min_len=2)
    return keywords


def keyphrases_extraction(text):
    tr4w = TextRank4Keyword()
    tr4w.analyze(text=text, window=2, lower=True, vertex_source='all_filters', edge_source='no_stop_words',
                 pagerank_config={'alpha': 0.85, })
    keyphrases = tr4w.get_keyphrases(keywords_num=6, min_occur_num=1)
    return keyphrases'''


def keywords_textrank(text):
    keywords = jieba.analyse.textrank(text, topK=6, allowPOS=('ns', 'n', 'a'))
    return keywords


if __name__ == "__main__":
    text = "现场已有多人受伤，尚且不知引爆了何种物质，案发现场有黑色痕迹，有刺激性气味"
    keywords = keywords_textrank(text)
    print(keywords)
