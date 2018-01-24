from .models import Question, Choice
from rest_framework import serializers


class ChoiceSerializer(serializers.ModelSerializer):
    question = serializers.StringRelatedField(read_only=True)

    class Meta:
        model = Choice
        fields = ('choice_text', 'votes', 'question')


class QuestionSerializer(serializers.ModelSerializer):
    choices = ChoiceSerializer(many=True, read_only=True)

    class Meta:
        model = Question
        fields = ('question_text', 'pub_date', 'choices')
